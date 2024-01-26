package com.example.saucedemo;

import Pages.LoginPage;
import Pages.MainPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Utils.SauceApp;

import java.time.Duration;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class MainPageTest {
    private WebDriver driver;
    private MainPage mainPage;
    String url = SauceApp.BASE_URL;
    String defaultSortOption = SauceApp.DEFAULT_SORTING;
    String user = "standard_user";

    @BeforeTest
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--headless=new");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get(url);
        mainPage = new MainPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginUser(user);
    }

    @AfterMethod
    public void resetStoreState() {
        mainPage.burgerButton.click();
        mainPage.resetButton.click();
        driver.navigate().refresh();
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void addOneItemToCart() {
        assertTrue(mainPage.cart.findElements(By.className("shopping_cart_badge")).isEmpty(), "Expected cart should be empty");
        mainPage.addItemToCart(mainPage.inventoryItems.get(0));
        assertEquals(Integer.parseInt(mainPage.cartItemsNum.getText()), 1, "Expected, only 1 item in the cart");
    }


    @Test
    public void addAllItemsToCart() {
        int amount = 0;
        for (WebElement item : mainPage.inventoryItems) {
            mainPage.addItemToCart(item);
            amount++;
        }
        assertEquals(Integer.parseInt(mainPage.cartItemsNum.getText()), amount);
    }

    @Test
    public void removeLastItemFromCart() {
        WebElement firstItem = mainPage.inventoryItems.get(0);
        mainPage.addItemToCart(firstItem);
        mainPage.removeItemFromCart(firstItem);
        assertTrue(mainPage.cart.findElements(By.className("shopping_cart_badge")).isEmpty(), "Expected cart should be empty");
    }

    @Test
    public void removeOneOfItemsFromCart() {
        int amount = 0;
        for (WebElement item : mainPage.inventoryItems) {
            mainPage.addItemToCart(item);
            amount++;
        }
        WebElement firstItem = mainPage.inventoryItems.get(0);
        mainPage.removeItemFromCart(firstItem);
        assertEquals(Integer.parseInt(mainPage.cartItemsNum.getText()), amount - 1);
    }

    @Test
    public void checkImagesAspectRatiosForBreakPoints() {
        WebElement firstItem = mainPage.inventoryItems.get(0);
        WebElement image = firstItem.findElement(By.className("inventory_item_img"));
        int width = image.getSize().getWidth();
        int height = image.getSize().getHeight();
        float res = Math.abs(((float) (3 * width) / height / 2 - 1) * 100);
        assertTrue(res < 10,
                "Wrong image aspect ratio");
    }

    @Test
    public void checkDefaultItemsSorting() {
        String currOption = mainPage.sortSelect.findElement(By.className("active_option")).getText();
        assertEquals(defaultSortOption, currOption);
        assertEquals(mainPage.inventoryNames(),mainPage.sortedInventoryNames(), "Wrong sorting order");
    }
    @Test
    public void checkZAItemsSorting() {
        mainPage.sortChange("za");
        assertEquals(mainPage.inventoryNames(),mainPage.reverseSortedInventoryNames(), "Wrong sorting order");
    }
    @Test
    public void checkAZItemsSorting() {
        mainPage.sortChange("za");
        mainPage.sortChange("az");
        assertEquals(mainPage.inventoryNames(),mainPage.sortedInventoryNames(), "Wrong sorting order");
    }
    @Test
    public void checkLoHiItemsSorting() {
        mainPage.sortChange("lohi");
        assertEquals(mainPage.inventoryPrices(),mainPage.sortedInventoryPrices(), "Wrong sorting order");
    }
    @Test
    public void checkHiLoItemsSorting() {
        mainPage.sortChange("hilo");
        assertEquals(mainPage.inventoryPrices(),mainPage.reverseSortedInventoryPrices(), "Wrong sorting order");
    }
}