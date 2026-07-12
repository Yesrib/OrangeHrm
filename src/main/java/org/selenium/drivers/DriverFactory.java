package org.selenium.drivers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.selenium.utils.ConfigReader;
import java.net.MalformedURLException;
import java.net.URL;

public final class DriverFactory {

    private DriverFactory() {}

    /**
     * Factory method to generate the appropriate WebDriver instance.
     * @return WebDriver
     */
    public static WebDriver createDriverInstance() {
        // Read configuration settings (Fallback to Chrome/Local if not specified)
        String browser = ConfigReader.getProperty("browser") != null ? ConfigReader.getProperty("browser").toLowerCase() : "chrome";
        String execution = ConfigReader.getProperty("execution") != null ? ConfigReader.getProperty("execution").toLowerCase() : "local";

        if (execution.equalsIgnoreCase("remote")) {
            return createRemoteDriver(browser);
        } else {
            return createLocalDriver(browser);
        }
    }

    private static WebDriver createLocalDriver(String browser) {
        return switch (browser) {
            case "firefox" -> new FirefoxDriver();
            case "chrome" -> {
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--remote-allow-origins=*");
                yield new ChromeDriver(options);
            }
            default -> throw new IllegalArgumentException("Browser type " + browser + " is not supported locally.");
        };
    }

    private static WebDriver createRemoteDriver(String browser) {
        // The default port for modern Selenium 4 Grid is 4444
        String gridUrl = "http://localhost:4444/wd/hub";

        try {
            return switch (browser) {
                case "chrome" -> {
                    ChromeOptions options = new ChromeOptions();
                    yield new RemoteWebDriver(new URL(gridUrl), options);
                }
                case "firefox" -> {
                    FirefoxOptions options = new FirefoxOptions();
                    yield new RemoteWebDriver(new URL(gridUrl), options);
                }
                default -> throw new IllegalArgumentException("Browser type " + browser + " is not supported on Remote Grid.");
            };
        } catch (MalformedURLException e) {
            throw new RuntimeException("Grid URL is invalid: " + gridUrl, e);
        }
    }
}