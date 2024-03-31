package stepDefinitions;

import enums.USERCREDENTIAL;
import io.cucumber.java.en.When;
import io.restassured.specification.RequestSpecification;
import utilities.API_Utils;

import static io.restassured.RestAssured.given;
import static utilities.API_Utils.response;

public class US_008_GetAllSMS_StepDefs {

    @When("user can get all SMS history {string}")
    public void userCanGetAllSMSHistory(String pathParam) {
        RequestSpecification requestSpecification = API_Utils.preSetUp(USERCREDENTIAL.TR2);

        response = given()
                .spec(requestSpecification)
                .when()
                .get(pathParam);
    }
}
