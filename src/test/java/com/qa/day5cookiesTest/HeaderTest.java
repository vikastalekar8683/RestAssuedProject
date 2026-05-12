package com.qa.day5cookiesTest;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.notNullValue;

public class HeaderTest {

    @Test
    public void testHeadersResponse() {
        Response response = given()
                .when()
                .get("https://www.google.com/")
                .then()
                .log().headers()
                .statusCode(200)
                .header("Content-Type", containsString("text/html"))
                .header("Content-Encoding", notNullValue())
                .header("Content-Encoding", "gzip")
                .header("X-Frame-Options", "SAMEORIGIN")
                .header("Server", "gws")
                .extract().response();

        // Extract Specific header information
        System.out.println("Extract Specific header information");
        String headerValue = response.getHeader("Content-Type");
        System.out.println("Value of header Content-Type : " + headerValue);

        //Extract all the headers
        System.out.println("Extract all the headers");
        Headers headers = response.getHeaders();
        for (Header h: headers){
            System.out.println(h.getName() + "  " + h.getValue());
        }


    }

}
