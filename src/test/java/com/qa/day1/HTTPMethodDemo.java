package com.qa.day1;

import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

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


public class HTTPMethodDemo {

    int userId;

    //Test to retrieve users and validate response
    @Test(priority = 1, enabled = true)
    void getUsers() {
        given()
                .header("x-api-key", "reqres_57146a59c8e84d1e9893ba155cb5154a")
                .when()
                .get("https://reqres.in/api/users")
                .then()
                .statusCode(200)
                .body("page", equalTo(1))
                .body(containsString("email"))
                .header("content-Type", equalTo("application/json; charset=utf-8"))
                .time(lessThan(5000L))
                .log().all();
    }


    //Test to create users and validate response
    @Test(priority = 2)
    void createUsers() {

        HashMap<String, String> data = new HashMap<String, String>();
        data.put("name","Vikas123");
        data.put("job","Software Tester");

        userId = given()
                .header("x-api-key", "reqres_57146a59c8e84d1e9893ba155cb5154a")
                .contentType("application/json")
                .body(data)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .statusCode(201)
                .header("content-Type", equalTo("application/json; charset=utf-8"))
                .time(lessThan(5000L))
                .body("name", equalToIgnoringCase("Vikas123"))
                .body("job", equalTo("Software Tester"))
                .body(containsString("id"))
                .log().all()
                .extract().jsonPath().getInt("id");

    }

    //Test to update users and validate response
    @Test(priority = 3, dependsOnMethods = "createUsers")
    void updateUsers() {

        HashMap<String, String> data = new HashMap<String, String>();
        data.put("name","Vikas123");
        data.put("job","Software Tester");

        given()
                .header("x-api-key", "reqres_57146a59c8e84d1e9893ba155cb5154a")
                .contentType("application/json")
                .body(data)
                .when()
                .put("https://reqres.in/api/users/"+userId)
                .then()
                .statusCode(200)
                .header("content-Type", equalTo("application/json; charset=utf-8"))
                .time(lessThan(5000L))
                .body("name", equalToIgnoringCase("Vikas123"))
                .body("job", equalTo("Software Tester"))
                .log().all();
    }

    //Test to delete users and validate response
    @Test(priority = 4, dependsOnMethods = {"createUsers", "updateUsers"})
    void deleteUsers() {

        given()
                .header("x-api-key", "reqres_57146a59c8e84d1e9893ba155cb5154a")
                .contentType("application/json")
                .when()
                .delete("https://reqres.in/api/users/"+userId)
                .then()
                .statusCode(204)
                .time(lessThan(5000L))
                .body(emptyOrNullString())
                .log().all();
    }




}
