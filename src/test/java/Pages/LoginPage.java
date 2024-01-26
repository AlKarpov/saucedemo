package Pages;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.FileReader;

public class LoginPage {
    @FindBy(id = "user-name")
    private WebElement userName;

    @FindBy(id = "password")
    private WebElement password;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(className = "error-message-container")
    public WebElement loginErrorMessage;

    public void loginUser(String user) {
        String username = "";
        String pass = "";
        try {
            FileReader reader = new FileReader("./src/test/java/Fixtures/Users.json");
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
            JSONObject userObject = (JSONObject) jsonObject.get(user);
            username = (String) userObject.get("username");
            pass = (String) userObject.get("password");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        userName.sendKeys(username);
        password.sendKeys(pass);
        loginButton.click();

    }

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
