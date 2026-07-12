package org.selenium.tests;

import org.openqa.selenium.WebDriver;
import org.selenium.base.BaseTest;
import org.selenium.drivers.DriverManager;
import org.testng.annotations.Test;
import org.selenium.pages.LoginPage;
import org.selenium.utils.ConfigReader;

public class LoginTest extends BaseTest {

    @Test(description="verify login functionality")
    public void testLogin() {
        // Test implementation here
        String username = ConfigReader.getProperty("login.username");
        String password = ConfigReader.getProperty("login.password");

         LoginPage loginPage = new LoginPage(DriverManager.getDriver())
                 .enterUsername(username)
                 .enterPassword(password)
                 .clickLoginButton();

    }
}
