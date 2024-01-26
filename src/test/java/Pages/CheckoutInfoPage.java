package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutInfoPage {
    @FindBy(id = "first-name")
    public WebElement firstName;
    @FindBy(id = "last-name")
    public WebElement lastName;
    @FindBy(id = "postal-code")
    public WebElement postalCode;
    @FindBy(id = "continue")
    public WebElement continueButton;

    public void submitUserInfoForm(String fName,String lName, String pCode){
        firstName.sendKeys(fName);
        lastName.sendKeys(lName);
        postalCode.sendKeys(pCode);
        continueButton.click();
    }

    public CheckoutInfoPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
