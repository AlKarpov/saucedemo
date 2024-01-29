package com.example.saucedemo;

import Pages.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import Utils.Helpers;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class HappyPathTest extends BaseTest{
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
    }

    @AfterMethod
    public void resetStoreState() {
        mainPage.burgerButton.click();
        mainPage.logoutButton.click();
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }

    @DataProvider(name = "data-provider")
    public Object[][] dpMethod(){
        return new Object[][] {{"standard_user"}, {"problem_user"},{"performance_glitch_user"}};
    }
    @Test(dataProvider = "data-provider", description = "E2E test of the buying random items process")
    public void HappyPath(String val) {
        LoginPage loginPage = new LoginPage(driver);
        CartPage cartPage = new CartPage(driver);
        Helpers helpers = new Helpers();
        long start = System.currentTimeMillis();
        loginPage.loginUser(val);
        long finish = System.currentTimeMillis();
        assertTrue((finish - start)<5000,"Login process lasts more than 5 seconds");
        assertTrue(driver.getCurrentUrl().contains("/inventory.html"));
        CheckoutInfoPage checkoutInfoPage = new CheckoutInfoPage(driver);
        CheckoutOverviewPage checkoutOverviewPage = new CheckoutOverviewPage(driver);
        List<WebElement> inventory = mainPage.inventoryItems;
        int randomNum = ThreadLocalRandom.current().nextInt(1, mainPage.inventoryItems.size());
        List<Integer> indexesToBuy = helpers.selectRandom(inventory.size(), randomNum);
        List<String> namesToBuy = indexesToBuy.stream().map(i -> inventory.get(i).findElement(By.className("inventory_item_name")).getText()).toList();
        List<String> pricesToBuy = indexesToBuy.stream().map(i -> inventory.get(i).findElement(By.className("inventory_item_price")).getText()).toList();
        indexesToBuy.forEach(i -> mainPage.addItemToCart(mainPage.inventoryItems.get(i)));
        mainPage.cart.click();
        //Cart
        assertTrue(driver.getCurrentUrl().contains("/cart.html"));
        assertEquals(namesToBuy,cartPage.getCartNames(), "Unexpected item name in cart");
        assertEquals(pricesToBuy,cartPage.getCartPrices(), "Unexpected item price in cart");
        cartPage.checkoutButton.click();
        //CheckoutInfo
        assertTrue(driver.getCurrentUrl().contains("/checkout-step-one.html"));
        checkoutInfoPage.submitUserInfoForm("Alex","K.","Y6R 8K7");
        //CheckoutOverview
        assertTrue(driver.getCurrentUrl().contains("/checkout-step-two.html"));
        assertEquals(namesToBuy,checkoutOverviewPage.getCartNames(), "Unexpected item name in checkout");
        assertEquals(pricesToBuy,checkoutOverviewPage.getCartPrices(), "Unexpected item price in checkout");
        Float subtotalExpected = checkoutOverviewPage.getSubtotal();
        Float taxExpected = checkoutOverviewPage.getTax(subtotalExpected);
        Float totalExpected = checkoutOverviewPage.getTotal(subtotalExpected,taxExpected);

        Float subtotalActual = helpers.getFloatFromString(checkoutOverviewPage.subtotal.getText());
        Float taxActual = helpers.getFloatFromString(checkoutOverviewPage.tax.getText());
        Float totalActual = helpers.getFloatFromString(checkoutOverviewPage.total.getText());

        assertEquals(subtotalExpected,subtotalActual, "Wrong sum of item prices");
        assertEquals(taxExpected,taxActual, "Wrong taxes");
        assertEquals(totalExpected,totalActual, "Wrong total amount");
        checkoutOverviewPage.finishButton.click();
        //CheckoutComplete
        assertTrue(driver.getCurrentUrl().contains("/checkout-complete.html"));

    }
}
