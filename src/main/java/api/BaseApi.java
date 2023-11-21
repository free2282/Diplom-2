package api;

import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static config.Config.MAIN_URL;
import static io.restassured.RestAssured.given;

public class BaseApi {

    protected RequestSpecification baseRequest() {
        return given()
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter())
                .contentType(ContentType.JSON).baseUri(MAIN_URL);
    }
}
// ctrl + p; ctrl + q; ctrl shift space