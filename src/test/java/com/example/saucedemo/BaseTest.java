package com.example.saucedemo;

import Utils.SauceApp;
import org.openqa.selenium.WebDriver;

public class BaseTest {
        public WebDriver driver;
        public Pages.MainPage mainPage;
        String url = SauceApp.BASE_URL;
        String defaultSortOption = SauceApp.DEFAULT_SORTING;
        String user = "standard_user";
}
