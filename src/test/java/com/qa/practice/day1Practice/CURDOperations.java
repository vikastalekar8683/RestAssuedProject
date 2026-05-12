package com.qa.practice.day1Practice;

import io.restassured.http.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CURDOperations {

    private static final Logger log = LoggerFactory.getLogger(CURDOperations.class);
    String userId;

    /*
    Pre-condition --> given() --> content Type, set cookies, add auth, add param, set headers info etc

    Action/Steps --> when() --> GET, POST, PUT, DELETE, PATCH

    Validation --> then() --> Validation status code, extract response, extract headers cookies & response body...
    */
    /*
    Validations
    -status code
    -Response body
    -Response Body
    -Response Time
    -Content-Type
    -Response String
     */

    /**
     * header("x-api-key", "reqres_57146a59c8e84d1e9893ba155cb5154a")
     * URL = "https://reqres.in/api/users"
     */

    @Test(priority = 1)
    public void getUsers() {
        given()
                .baseUri("https://reqres.in")
                .header("x-api-key", "reqres_57146a59c8e84d1e9893ba155cb5154a")
                .when()
                .get("/api/users")
                .then()
                .statusCode(200)
                .body("page", equalTo(1))
                .body(containsString("email"))
                .time(lessThan(5000L))
                .header("content-type", equalTo("application/json; charset=utf-8"))
                .log().all();
    }


    @Test(priority = 2)
    public void createUser() {
        String payload = "{\"name\":\"Reyansh\",\"job\":\"CA\"}";
        userId = given()
                .baseUri("https://reqres.in")
                .header("x-api-key", "reqres_57146a59c8e84d1e9893ba155cb5154a")
                .contentType("application/json")
                .body(payload)
                .when()
                .post("/api/users")
                .then()
                .statusCode(201)
                .time(lessThan(5000L))
                .header("content-type", equalTo("application/json; charset=utf-8"))
                .body("name", equalTo("Reyansh"))
                .body("job", equalTo("CA"))
                .log().body()
                .extract().jsonPath().get("id");
    }

    @Test(priority = 3, dependsOnMethods = "createUser")
    public void getUserByID() {
        int existingUserId = 2;  // Using existing user ID (reqres.in mock API has users 1-12)
        given()
                .baseUri("https://reqres.in")
                .header("x-api-key", "reqres_57146a59c8e84d1e9893ba155cb5154a")
                .contentType("application/json")
                .when()
                .get("/api/users/" + existingUserId)
                .then()
                .statusCode(200)
                .time(lessThan(5000L))
                .header("content-type", equalTo("application/json; charset=utf-8"))
                .body("data.id", equalTo(existingUserId))
                .log().body();
    }

    @Test(priority = 4, dependsOnMethods = "createUser")
    public void updateUser() {
        String payload = "{\"name\":\"Reyansh\",\"job\":\"CA1\"}";
        int existingUserId = 2;  // Using existing user ID (reqres.in mock API has users 1-12)

        given()
                .baseUri("https://reqres.in")
                .header("x-api-key", "reqres_57146a59c8e84d1e9893ba155cb5154a")
                .contentType("application/json")
                .body(payload)
                .when()
                .put("/api/users/"+existingUserId)
                .then()
                .statusCode(200)
                .time(lessThan(5000L))
                .header("content-type", "application/json; charset=utf-8")
                .body("name", equalTo("Reyansh"))
                .body("job", equalTo("CA1"))
                .log().body();
    }

}
