package stepDefinitions;

import io.cucumber.java.en.When;
import pages.CommonPage;

public class US_003_SendTestSMS_StepDefs extends CommonPage {
    @When("User send test SMS {string} to {string}")
    public void userSendTestSMSTo(String message, String telNo) {
        getEmailSMSPage().sendTestSMS(message, telNo);
    }
}
