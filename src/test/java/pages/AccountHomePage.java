package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utilities.ReusableMethods;

public class AccountHomePage extends CommonPage {

    @FindBy(css = "[href='/dashboard/email-sms']")
    public WebElement emailSMSsection;
    @FindBy(xpath = "//label[@class='ant-segmented-item'][.='SMS']")
    public WebElement SMSsection;

    //istenilenbutona tıklama işlemi yapar
    public void cliclElement(WebElement webElement){
        ReusableMethods.scrollToElement(webElement);
        ReusableMethods.waitForClickablility(webElement,10);
        webElement.click();
    }

}
