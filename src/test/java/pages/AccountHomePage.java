package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utilities.ReusableMethods;

public class AccountHomePage extends CommonPage {

    @FindBy(css = "[href='/dashboard/email-sms']")
    public WebElement emailSMSsection;
    @FindBy(css = "[title='SMS']")
    public WebElement SMSsection;

    //istenilenbutona tıklama işlemi yapar
    public void cliclElement(WebElement webElement){
        ReusableMethods.waitForClickablility(webElement,10);
        webElement.click();
    }

}
