package utilities;

import enums.USERCREDENTIAL;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class API_Utils {
    static ResponseSpecification responseSpecification;
    static RequestSpecification requestSpecification;
    public static Response response;
    private static String token = "";

//    private GetAddress getAddress;
//
//    public GetAddress getGetAddress() {
//        if (getAddress == null) {
//            getAddress = new GetAddress(token);
//        }
//        return getAddress;
//    }

    public static RequestSpecification preSetUp(USERCREDENTIAL usercredential) {
        requestSpecification = RestAssured.given();
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.baseUri("https://test.urbanicfarm.com");
        requestSpecification.basePath("/api");

        Map<String, String> map = new HashMap<>();
        map.put("email", usercredential.getUsername());
        map.put("password", usercredential.getPassword());

        response = given()
                .contentType(ContentType.JSON)
                .body(map)
                .when()
                .post("https://test.urbanicfarm.com/api/public/login");

        token = response.jsonPath().getString("token");
        requestSpecification.auth().oauth2(token);

       return requestSpecification;

    }

    public static ResponseSpecification endSetUp() {

        responseSpecification = RestAssured.expect();
        responseSpecification.statusCode(200);
        responseSpecification.body("success", equalTo(true));
        responseSpecification.time(Matchers.lessThan(2000L));

       return responseSpecification;
    }


}
