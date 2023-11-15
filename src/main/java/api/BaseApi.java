package api;

import io.qameta.allure.Step;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class BaseApi
{
    @Step("Базовая запросов")
    protected RequestSpecification baseRequest()
    {
        return given()
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter())
                .contentType(ContentType.JSON);
    }
}
// ctrl + p; ctrl + q; ctrl shift space