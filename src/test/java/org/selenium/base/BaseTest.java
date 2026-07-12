package org.selenium.base;

import org.selenium.drivers.DriverManager;
import org.selenium.drivers.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.selenium.utils.ConfigReader;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public class BaseTest {

    // Protected so child test classes can easily fetch it, though DriverManager.getDriver() works anywhere!
    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        // 1. Initialize the driver instance via factory
        WebDriver driverInstance = DriverFactory.createDriverInstance();

        // 2. Secure it safely within the ThreadLocal manager
        DriverManager.setDriver(driverInstance);

        // 3. Make it available to the local class context
        this.driver = DriverManager.getDriver();

        this.driver.manage().window().maximize();
        int timeout = Integer.parseInt(ConfigReader.getProperty("timeout"));
        DriverManager.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));

        // 4. Navigate to app url
        DriverManager.getDriver().get(ConfigReader.getProperty("app.url"));
    }

    @AfterMethod
    public void tearDown() {
        if (DriverManager.getDriver() != null) {
            // Quit the browser session
            DriverManager.getDriver().quit();

            // Wipe the ThreadLocal map clean to prevent memory leaks
            DriverManager.unload();
        }
    }
}