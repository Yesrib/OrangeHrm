package org.selenium.drivers;

import org.openqa.selenium.WebDriver;

public final class DriverManager {

    //prevent instantiation of this utility class
    private DriverManager() {}

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static void setDriver(WebDriver webDriver) {
        driver.set(webDriver);
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    // Removes the driver from the ThredLocal map
    // crucial to call this during teardown to prevent memory leaks

    public static void unload() {
        driver.remove();
    }
}