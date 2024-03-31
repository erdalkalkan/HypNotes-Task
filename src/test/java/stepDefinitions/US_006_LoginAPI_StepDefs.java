package stepDefinitions;

import enums.USERCREDENTIAL;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import utilities.API_Utils;

import java.util.Map;

import static utilities.API_Utils.response;

public class US_006_LoginAPI_StepDefs {

    @Given("user connect to the Hypnotes logged in page")
    public void userConnectToTheHypnotesLoggedInPage() {
        String token = API_Utils.getToken(USERCREDENTIAL.TR2);
        System.out.println("token = " + token);
    }

    @Then("user verifies status code {int}")
    public void userVerifiesStatusCode(int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        Assert.assertEquals(expectedStatusCode,actualStatusCode);
    }
}
