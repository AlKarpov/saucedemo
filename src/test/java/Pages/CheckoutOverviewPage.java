package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.text.DecimalFormat;
import java.util.List;

public class CheckoutOverviewPage {
    private static final DecimalFormat df = new DecimalFormat("0.00");

    @FindBy(className="cart_item")
    public List<WebElement> itemsInCart;

    public List<String> getCartNames() {
        return itemsInCart.stream().map(i -> i.findElement(By.className("inventory_item_name")).getText()).toList();
    }
    public List<String> getCartPrices() {
        return itemsInCart.stream().map(i -> i.findElement(By.className("inventory_item_price")).getText()).toList();
    }

    @FindBy(className = "summary_subtotal_label")
    public WebElement subtotal;
    @FindBy(className = "summary_tax_label")
    public WebElement tax;
    @FindBy(css = "div[class='summary_info_label summary_total_label']")
    public WebElement total;

    public Float getSubtotal() {
        List<Float> floatPrices = getCartPrices().stream().map(e -> Float.parseFloat(e.replaceAll("[^0-9/.]", ""))).toList();
        Float subtotal = 0.00f;
        for(Float p : floatPrices) {
            subtotal+=p;
        }
        return subtotal;
    }
    public Float getTax(Float subtotal){
        return Float.parseFloat(df.format(subtotal*0.08));
    }
    public Float getTotal(Float subtotal,Float tax){
        return subtotal+tax;
    }
    public CheckoutOverviewPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(id="finish")
    public WebElement finishButton;
}
