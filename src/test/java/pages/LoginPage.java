package pages;

import enums.URL_LINKS;
import enums.USERCREDENTIAL;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static stepDefinitions.Hooks.driver;
import static utilities.ReusableMethods.*;

public class LoginPage extends CommonPage {

    @FindBy(css = "[data-test-id='loginAsClientPage_email']")
    public WebElement emailInput;

    @FindBy(css = "[data-test-id='loginAsClientPage_password']")
    public WebElement passwordInput;

    @FindBy(css = "[data-test-id='loginAsClientPage_loginButton']")
    public WebElement loginBtn;

    @FindBy(css = ".ant-btn-sm.ant-btn-background-ghost>span")
    public WebElement timeZoneYesBtn;

    @FindBy(xpath = "//strong[.='Online - Europe/Berlin']")
    public WebElement timeZone;

    //Enum classını yazarak credentiallerinle login olabilirsin
    public void login(USERCREDENTIAL usercredential) {
        driver.get(URL_LINKS.LOGIN_URL.getLink());
        waitForPageToLoad(5);
        emailInput.sendKeys(usercredential.getUsername());
        passwordInput.sendKeys(usercredential.getPassword());
        loginBtn.click();
        waitForPageToLoad(5);
        boolean flag = false;
        if (timeZone.getText().equals("Online - Europe/Berlin")) {
            System.out.println("Time Zone pop-up çıkmadı!");
        }else {
            try {
                waitForVisibility(timeZoneYesBtn, 3);
                flag = timeZoneYesBtn.isDisplayed();
            } catch (NoSuchElementException | TimeoutException ignored) {
            }

            if (flag) {
                waitForClickablility(timeZoneYesBtn, 10);
                timeZoneYesBtn.click();
            }
        }
        waitFor(1);
    }


}
