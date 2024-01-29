package com.example.saucedemo;

import Pages.LoginPage;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.testng.Assert.assertTrue;

public class LoginPageLockedUserLoginTest extends BaseTest {

    @BeforeTest
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--headless=new");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get(url);

    }
    @AfterTest
    public void tearDown() {
        driver.quit();
    }
    @Test
    public void logInAsLockedUser() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginUser(user);
        assertTrue(loginPage.loginErrorMessage.isDisplayed());
    }
}
