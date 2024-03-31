package stepDefinitions;

import enums.USERCREDENTIAL;
import io.cucumber.java.en.When;
import io.restassured.specification.RequestSpecification;
import utilities.API_Utils;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static utilities.API_Utils.response;

public class US_007_SendSMS_StepDefs {

    @When("user can send SMS at {string}")
    public void userCanSendSMSAt(String pathParam) {
        RequestSpecification requestSpecification = API_Utils.preSetUp(USERCREDENTIAL.TR2);

        Map<String, Object> map = new HashMap<>();
        map.put("message", "Hallo, wie gehts?");
        map.put("clients[]", 5665);

        response = given()
                .spec(requestSpecification)
                .formParams(map)  //.contentType("application/x-www-form-urlencoded") --> .formParams(formParams)
                .when()
                .post(pathParam);
    }
}
