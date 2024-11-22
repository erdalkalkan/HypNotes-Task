package pages;

import enums.URL_LINKS;
import enums.USERCREDENTIAL;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utilities.ReusableMethods;

import static stepDefinitions.Hooks.driver;
import static utilities.ReusableMethods.*;

public class LoginPage extends CommonPage {

    @FindBy(css = "[data-test-id='loginAsClientPage_email']")
    private WebElement emailInput;

    @FindBy(css = "[data-test-id='loginAsClientPage_password']")
    private WebElement passwordInput;

    @FindBy(css = "[data-test-id='loginAsClientPage_loginButton']")
    private WebElement loginBtn;

    @FindBy(css = ".ant-btn-sm.ant-btn-background-ghost>span")
    private WebElement timeZoneYesBtn;

    @FindBy(xpath = "//strong[.='Online - Europe/Berlin']")
    private WebElement timeZone;

    //Enum classını yazarak credentiallerinle login olabilirsin
    public void
    login(USERCREDENTIAL usercredential) {
        driver.get(URL_LINKS.LOGIN_URL.getLink());
        waitForPageToLoad(5);
        emailInput.sendKeys(usercredential.getUsername());
        passwordInput.sendKeys(usercredential.getPassword());
        loginBtn.click();
        waitForPageToLoad(5);
        ReusableMethods.waitForVisibility(timeZone,5);
        boolean flag = false;
        if (!timeZone.getText().contains("Online - Europe/Berlin")) {
            try {
                waitForVisibility(timeZoneYesBtn, 5);
                flag = timeZoneYesBtn.isDisplayed();
            } catch (NoSuchElementException | TimeoutException ignored) {
            }

            if (flag) {
                waitForClickablility(timeZoneYesBtn, 10);
                timeZoneYesBtn.click();
            }
        }else {
            System.out.println("Time Zone pop-up çıkmadı!");
        }
        waitFor(1);
    }


}
