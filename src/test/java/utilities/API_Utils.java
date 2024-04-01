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
        //requestSpecification.contentType(ContentType.JSON);
        requestSpecification.baseUri("https://test.hypnotes.net/");
        requestSpecification.basePath("api/");

        token = getToken(usercredential);
        requestSpecification.header("cookie",token);
        requestSpecification.header("content-type", "application/x-www-form-urlencoded");

       return requestSpecification;

    }

    public static ResponseSpecification endSetUp() {

        responseSpecification = RestAssured.expect();
        responseSpecification.statusCode(200);
        responseSpecification.body("success", equalTo(true));
        responseSpecification.time(Matchers.lessThan(2000L));

       return responseSpecification;
    }

    public static String getToken(USERCREDENTIAL userinfo) {

        response = given()
                .header("content-type", "application/x-www-form-urlencoded")
                .body("{\"username\":\"" + userinfo.getUsername() + "\",\"password\":\"" + userinfo.getPassword() + "\"}")
                .post("https://test.hypnotes.net/api/login");

        String csrfToken = "csrfToken=" + response.getCookie("csrfToken") +";" ;
        String PHPSESSID = "PHPSESSID=" + response.getCookie("PHPSESSID") +";" ;
        token= csrfToken+PHPSESSID;

        return token;
    }

}
