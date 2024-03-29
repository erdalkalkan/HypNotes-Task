package stepDefinitions;

import enums.USERCREDENTIAL;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.CommonPage;
import utilities.ReusableMethods;

import java.util.List;

public class US_001_SendSMS_StepDefs extends CommonPage {

    @Given("User login as tr2")
    public void user_login_as_tr2() {
        getLoginPage().login(USERCREDENTIAL.TR2);
        //ReusableMethods.waitFor(4);
    }

    @Given("User goes to E-mail and SMS section")
    public void user_goes_to_e_mail_and_sms_section() {
        getAccountHomePage().cliclElement(getAccountHomePage().emailSMSsection);
        //ReusableMethods.waitFor(4);
    }

    @Given("User clicks on Send SMS section")
    public void user_clicks_on_send_sms_section() {
        getAccountHomePage().cliclElement(getAccountHomePage().SMSsection);
        ReusableMethods.waitFor(4);
    }

    @When("User send a SMS with valid data")
    public void userSendASMSWithValidData(DataTable dataTable) {
        String message = dataTable.row(0).getFirst();
        String email = dataTable.row(0).getLast();
        getEmailSMSPage().sendSMS(message, email);
    }

    @Then("User should see a success message")
    public void userShouldSeeASuccessMessage() {
        getEmailSMSPage().assertSmsSuccessMsj();
    }
}
