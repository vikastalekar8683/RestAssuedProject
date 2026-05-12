package com.qa.day5cookiesTest;

import io.restassured.http.Cookie;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class CookiesTest {

    @Test
    public void testCookiesResponse() {
        Response response = given()
                .when()
                .get("https://www.google.com/")
                .then()
                .log().cookies()
                .statusCode(200)
                //.cookies("AEC", "AaJma5upVs_4QBRqyt-xV60XjIIib-30bizMHvH5dQKUN8MwX_Nksr5tzw") //Cookies value validation - Should Failed
                .cookies("AEC", notNullValue())
                .extract().response();

        // Extract a Specific cookies
        String cookievalue = response.getCookie("AEC");
        System.out.println("Value of AEC cookie : " + cookievalue);

        // Extract all the cookies

        Map<String, String> allCookies = response.getCookies();
        System.out.println("All the Cookies : " + allCookies);

        //Printing cookies and their values
        System.out.println("Printing cookies and their values");
        for (String key : allCookies.keySet()) {
            System.out.println(key + " : " + allCookies.get(key));
        }

        // Get Detailed information about cookies
        System.out.println("Get Detailed information about cookies");
        Cookie cookie_Info = response.getDetailedCookie("AEC");
        System.out.println(cookie_Info.hasExpiryDate());
        System.out.println(cookie_Info.getExpiryDate());
        System.out.println(cookie_Info.hasValue());
        System.out.println(cookie_Info.getValue());
        System.out.println(cookie_Info.isSecured());




    }

}
