package pages;

import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utilities.JSUtils;
import utilities.ReusableMethods;

import java.util.List;

import static stepDefinitions.Hooks.driver;

public class EmailSMSPage extends CommonPage {

    @FindBy(css = "#basic_message")
    private WebElement msjBox;
    @FindBy(css = "#basic_clients")
    private WebElement selectClientBox;
    @FindBy(xpath = "//p[.='erdal@yopmail.com']")
    private WebElement selectErdalEmail;
    @FindBy(css = "[type='submit']")
    private WebElement sendSMSBtn;
    @FindBy(xpath = "//span[contains(text(),'successfully')]")
    private WebElement successMsj;
    @FindBy(css = "#basic_checkAll")
    private WebElement selectAllClients;
    @FindBy(css = ".ant-checkbox.css-aqx16b")
    private WebElement selectTestSMSCheckBox;
    @FindBy(css = "[type='tel']")
    private WebElement telInput;
    @FindBy(xpath = "//div[.='Please Select Client']")
    private WebElement withoutSelectClient;
    @FindBy(xpath = "//button[.='Sent Sms-Box']")
    private WebElement sendSMSBoxBtn;
    @FindBy(css = ".anticon-delete")
    private List<WebElement> deleteBtn;
    @FindBy(css = "[class='ant-btn css-aqx16b ant-btn-primary ant-btn-sm']>span")
    private WebElement deleteYesBtn;
    @FindBy(xpath = "//div[.='No data']")
    private WebElement noData;

    //SMS gönderir
    //yazacağınız mesajı ve email adresini girmelisiniz
    public void sendSMS(String message, String email) {
        ReusableMethods.scrollAndClickWithJS(msjBox);
        msjBox.sendKeys(message);
        ReusableMethods.waitForVisibility(sendSMSBtn, 3);
        JSUtils.scrollDownByJS(sendSMSBtn);
        selectClientBox.sendKeys(email);
        ReusableMethods.waitForVisibility(selectErdalEmail, 3);
        selectErdalEmail.click();
        sendSMSBtn.click();
    }

    //Tüm müşterilere SMS gönderir
    //yazacağınız mesajı girmelisiniz
    public void sendSMSAllClient(String message) {
        ReusableMethods.scrollAndClickWithJS(msjBox);
        msjBox.sendKeys(message);
        ReusableMethods.waitForVisibility(sendSMSBtn, 3);
        JSUtils.scrollDownByJS(sendSMSBtn);
        selectAllClients.click();
        sendSMSBtn.click();
    }

    //SMS gönderdikten sonra gelen msjı kontrol eder
    public void assertSmsSuccessMsj() {
        ReusableMethods.waitForVisibility(successMsj, 5);
        Assert.assertTrue(successMsj.getText().contains("successfully"));
    }

    //Tüm müşterilere SMS gönderir
    //yazacağınız mesajı ve telefon numarasını girmelisiniz
    public void sendTestSMS(String message, String telNo) {
        ReusableMethods.scrollAndClickWithJS(msjBox);
        msjBox.sendKeys(message);
        JSUtils.scrollDownByJS(sendSMSBtn);
        ReusableMethods.waitForVisibility(sendSMSBtn, 3);
        selectTestSMSCheckBox.click();
        ReusableMethods.waitForVisibility(telInput, 3);
        telInput.clear();
        telInput.sendKeys(telNo);
        sendSMSBtn.click();
    }

    //Send SMS button clickable olduğunu kontrol eder
    public void checkSendSMSBtn() {
        JSUtils.scrollDownByJS(sendSMSBtn);
        ReusableMethods.waitForVisibility(sendSMSBtn, 3);
        sendSMSBtn.click();
        ReusableMethods.waitForVisibility(withoutSelectClient, 3);
        Assert.assertTrue("After click there is no error message"
                , withoutSelectClient.isDisplayed());
    }

    //Select All Clients switch button clickable olduğunu kontrol eder
    public void checkSelectAllClientsSwitchBtn() {
        JSUtils.scrollDownByJS(sendSMSBtn);
        ReusableMethods.waitForVisibility(selectAllClients, 3);
        selectAllClients.click();
        String actualAttributeName = selectAllClients.getAttribute("aria-checked");
        String expectedAttributeName = "true";
        Assert.assertEquals(expectedAttributeName, actualAttributeName);
    }

    //test SMS checkbox'u clickable olduğunu kontrol eder
    public void checkTestSMSCheckbox() {
        JSUtils.scrollDownByJS(sendSMSBtn);
        ReusableMethods.waitForVisibility(selectTestSMSCheckBox, 3);
        selectTestSMSCheckBox.click();
        Assert.assertTrue("TEL NO Input is not enable"
                , selectTestSMSCheckBox.isEnabled());
    }

    //test SMS checkbox'u clickable olduğunu kontrol eder
    public void checkMessageBox() {
        JSUtils.scrollAllUpByJS();
        ReusableMethods.waitForVisibility(msjBox, 3);
        Assert.assertTrue("Message Box is not enable"
                , msjBox.isEnabled());
    }

    //Send SMS box'u clickable olduğunu kontrol eder
    public void checkSendSMSBoxBtn() {
        JSUtils.scrollAllUpByJS();
        ReusableMethods.waitForVisibility(sendSMSBoxBtn, 3);
        sendSMSBoxBtn.click();
        ReusableMethods.waitForPageToLoad(5);
        ReusableMethods.waitFor(1);
        String currentUrl = driver.getCurrentUrl();
        String expectedUrl = "https://test.hypnotes.net/dashboard/send-sms/sent-smsbox";
        Assert.assertEquals(expectedUrl, currentUrl);

    }

    //Bütün kayıtlı SMSleri siler
    public void deleteAllSavedSMS() {
        int sizeSavedSMSList = deleteBtn.size();

        for (int i = 0; i < sizeSavedSMSList; i++) {
            ReusableMethods.waitFor(1);
            ReusableMethods.waitForClickablility(deleteBtn.getFirst(), 5);
            deleteBtn.getFirst().click();
            ReusableMethods.waitForVisibility(deleteYesBtn, 3);
            deleteYesBtn.click();
        }
        Assert.assertTrue("No Data görünmedi", noData.isDisplayed());
    }

}
