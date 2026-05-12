package com.qa.day3;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class AuthenticationTest {


    //1. Basic Authentication
    @Test
    public void verifyBasicAuthentication() {
        given()
                .auth().basic("postman", "password")
                .when()
                .get("https://postman-echo.com/basic-auth")
                .then()
                .statusCode(200)
                .body("authenticated", equalTo(true))
                .log().body();
    }


    //2. Basic Preemptive Authentication
    @Test
    public void verifyBasicPremtiveAuth() {
        given()
                .auth().preemptive().basic("postman", "password")
                .when()
                .get("https://postman-echo.com/basic-auth")
                .then()
                .statusCode(200)
                .body("authenticated", equalTo(true))
                .log().body();
    }


    //3. Digest Authentication
    @Test
    public void verifyDigestAuth() {
        given()
                .auth().digest("postman", "password")
                .when()
                .get("https://postman-echo.com/basic-auth")
                .then()
                .statusCode(200)
                .body("authenticated", equalTo(true))
                .log().body();
    }


    //3. Bearer Token Authentication
    @Test
    public void verifyBearerTokenAuth() {
        String bearerToken = "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";

        given()
                .header("Authorization", "Bearer " + bearerToken)
                .when()
                .get("https://api.github.com/user/repos")
                .then()
                .statusCode(200)
                .log().body();
    }

    // 5. API Key Authentication
    // API Key : 408660362c84ca8c33769a3629253367
    // URL: https://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={API key}
    @Test
    public void validateAPIKeyAuth() {
        String apiKey = "408660362c84ca8c33769a3629253367";
        String baseURI = "https://api.openweathermap.org";
        String basePath = "/data/2.5/weather";
        given()
                .queryParam("lat", "18.5204")     // Pune latitude
                .queryParam("lon", "73.8567")     // Pune longitude
                .queryParam("appid", apiKey)
                .when()
                .get(baseURI + basePath)
                .then()
                .statusCode(200)
                .log().body();
    }


}
