package stepDefinitions;

import io.cucumber.java.en.Then;
import pages.CommonPage;

public class US_004_SMSSectionFunctionallity_StepDefs extends CommonPage {
    @Then("User should be able to see Send SMS button clickable")
    public void userShouldBeAbleToSeeSendSMSButtonClickable() {
        getEmailSMSPage().checkSendSMSBtn();
    }

    @Then("User should be able to see Sellect All Clients switch box clickable")
    public void userShouldBeAbleToSeeSellectAllClientsSwitchBoxClickable() {
        getEmailSMSPage().checkSelectAllClientsSwitchBtn();
    }

    @Then("User should be able to see Test SMS check box clickable")
    public void userShouldBeAbleToSeeTestSMSCheckBoxClickable() {
        getEmailSMSPage().checkTestSMSCheckbox();
    }

    @Then("User should be able to see message box functional")
    public void userShouldBeAbleToSeeMessageBoxFunctional() {
        getEmailSMSPage().checkMessageBox();
    }

    @Then("User should be able to see Sent SMS Box button clickable")
    public void userShouldBeAbleToSeeSentSMSBoxButtonClickable() {
        getEmailSMSPage().checkSendSMSBoxBtn();
    }
}
