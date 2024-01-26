package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import Utils.Helpers;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class MainPage {
    @FindBy(className = "inventory_item")
    public List<WebElement> inventoryItems;

    @FindBy(id = "shopping_cart_container")
    public WebElement cart;

    @FindBy(className = "shopping_cart_badge")
    public WebElement cartItemsNum;

    public void addItemToCart(WebElement item) {
        int currCartAmount = 0;
        try {
            currCartAmount = Integer.parseInt(cartItemsNum.getText());
        } catch(Exception ignored) {}
        item.findElement(By.className("btn_primary")).click();
        assertEquals(item.findElements(By.className("btn_secondary")).size(),1, "'Add to cart' button should changes to Remove after click");
        assertEquals(Integer.parseInt(cartItemsNum.getText()),currCartAmount+1, "Expected cart items counter change");
    }
    public void removeItemFromCart(WebElement item) {
        int currCartAmount = Integer.parseInt(cartItemsNum.getText());
        item.findElement(By.className("btn_secondary")).click();
        assertTrue(item.findElement(By.className("btn_primary")).isDisplayed(), "Button should changes to Add after click");
        int resultCartAmount = 0;
        try {
            resultCartAmount = Integer.parseInt(cartItemsNum.getText());
        } catch(Exception ignored) {}
        assertEquals(resultCartAmount,currCartAmount-1, "Expected cart items counter change");
    }

    @FindBy(id = "react-burger-menu-btn")
    public WebElement burgerButton;

    @FindBy(id = "reset_sidebar_link")
    public WebElement resetButton;

    @FindBy(id = "logout_sidebar_link")
    public WebElement logoutButton;

    @FindBy(className = "select_container")
    public WebElement sortSelect;

    public void sortChange(String option) {
        sortSelect.click();
        sortSelect.findElement(By.xpath("//option[@value='"+option+"']")).click();
    }
    @FindBy(className = "inventory_item_name")
    private List<WebElement> itemNameList;
    @FindBy(className = "inventory_item_price")
    private List<WebElement> itemPriceList;

    Helpers helpers = new Helpers();
    public List<String> inventoryNames() {
        return itemNameList.stream().map(WebElement::getText).toList();
    }
    public List<Float> inventoryPrices() {
        return itemPriceList.stream().map(e -> helpers.getFloatFromString(e.getText())).toList();
    }

    public List<String> sortedInventoryNames() {
        return itemNameList.stream().map(WebElement::getText).sorted().toList();

    }
    public List<String> reverseSortedInventoryNames() {
        return itemNameList.stream().map(WebElement::getText).sorted().toList().reversed();
    }
    public List<Float> sortedInventoryPrices() {
        return itemPriceList.stream().map(e -> helpers.getFloatFromString(e.getText())).sorted().toList();
    }
    public List<Float> reverseSortedInventoryPrices() {
        return itemPriceList.stream().map(e -> helpers.getFloatFromString(e.getText())).sorted().toList().reversed();
    }
    public MainPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
