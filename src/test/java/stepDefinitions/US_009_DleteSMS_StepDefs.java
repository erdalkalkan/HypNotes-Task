package stepDefinitions;

import enums.USERCREDENTIAL;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import utilities.API_Utils;

import static io.restassured.RestAssured.given;
import static utilities.API_Utils.response;

public class US_009_DleteSMS_StepDefs {
    @When("user can delete added SMS at {string}")
    public void userCanDeleteAddedSMSAt(String pathParam) {
        RequestSpecification requestSpecification = API_Utils.preSetUp(USERCREDENTIAL.TR2);

        response = given()
                .spec(requestSpecification)
                .when()
                .get("hypnotherapist/sms/sms-history");

        JsonPath jsonPath = response.jsonPath();
        int smsID = jsonPath.getInt("smss[0].id");

        response = given()
                .spec(requestSpecification)
                .when()
                .post(pathParam+smsID);
    }
}
