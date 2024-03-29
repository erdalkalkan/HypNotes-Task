package stepDefinitions;

import io.cucumber.java.en.When;
import pages.CommonPage;

public class US_002_SendSMSAllClient_StepDefs extends CommonPage {
    @When("User send all client {string}")
    public void userSendAllClient(String message) {
        getEmailSMSPage().sendSMSAllClient(message);
    }


}
