package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CartPage {
    @FindBy(className="cart_item")
    public List<WebElement> itemsInCart;

    @FindBy(id = "continue-shopping")
    public WebElement continueShoppingButton;

    public List<String> getCartNames() {
        return itemsInCart.stream().map(i -> i.findElement(By.className("inventory_item_name")).getText()).toList();
    }
    public List<String> getCartPrices() {
        return itemsInCart.stream().map(i -> i.findElement(By.className("inventory_item_price")).getText()).toList();
    }

    @FindBy(id = "checkout")
    public WebElement checkoutButton;
    public CartPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
