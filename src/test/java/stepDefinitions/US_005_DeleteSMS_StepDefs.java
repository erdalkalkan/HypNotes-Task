package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import pages.CommonPage;

public class US_005_DeleteSMS_StepDefs extends CommonPage {
    @And("User clicks on Send SMS Box button")
    public void userClicksOnSendSMSBoxButton() {
        getEmailSMSPage().checkSendSMSBoxBtn();
    }

    @Then("User should be able to delete all saved SMS")
    public void userShouldBeAbleToDeleteAllSavedSMS() {
        getEmailSMSPage().deleteAllSavedSMS();
    }

}
