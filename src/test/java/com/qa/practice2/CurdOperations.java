package com.qa.practice2;


/*
Pre-condition --> given() --> content Type, set cookies, add auth, add param, set headers info etc

Action/Steps --> when() --> GET, POST, PUT, DELETE, PATCH

Validation --> then() --> Validation status code, extract response, extract headers cookies & response body...
*/
/*
Validations
-status code
-Response Body
-Response Time
-Content-Type
-Response String
 */

import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CurdOperations {

    String userid;

    @Test(priority = 1)
    public void getUsers() {
        given()
                .header("x-api-key", "reqres_813cf76619de49968b7a73ba2a35d0b1")
                .when()
                .get("https://reqres.in/api/users")
                .then()
                .statusCode(200)
                .time(lessThan(4000L))
                .header("content-Type", equalTo("application/json; charset=utf-8"))
                .body("page", equalTo(1))
                .log().body()
                .log().ifError();
    }

    @Test(priority = 2)
    public void createUser() {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("name", "Vikas");
        data.put("Job", "QA");
        userid = given()
                .header("x-api-key", "reqres_813cf76619de49968b7a73ba2a35d0b1")
                .contentType("application/json")
                .body(data)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .statusCode(201)
                .header("content-type", equalTo("application/json; charset=utf-8"))
                .body("name", equalTo("Vikas"))
                .body("Job", equalTo("QA"))
                .time(lessThan(5000L))
                .log().body()
                .extract().jsonPath().getString("id");
        System.out.println("User ID : " + userid);
    }

    //Test to update users and validate response
    @Test(priority = 3, dependsOnMethods = "createUser")
    public void updateUser(){
        HashMap<String,String> data = new HashMap<String, String>();
        data.put("name", "Vikas123");
        data.put("job", "QA123");
        given()
                .header("x-api-key", "reqres_813cf76619de49968b7a73ba2a35d0b1")
                .contentType("application/json")
                .body(data)
                .when()
                .put("https://reqres.in/api/users/"+userid)
                .then()
                .statusCode(200)
                .header("content-type", equalTo("application/json; charset=utf-8"))
                .body("name", equalTo("Vikas123"))
                .body("job", equalTo("QA123"))
                .time(lessThan(5000L))
                .log().body();
    }

    //Test to delete users and validate response
    @Test(priority = 4, dependsOnMethods = {"createUser", "updateUser"})
    public void deleteUser(){
        given()
                .header("x-api-key", "reqres_813cf76619de49968b7a73ba2a35d0b1")
                .contentType("application/json")
                .when()
                .delete("https://reqres.in/api/users/"+userid)
                .then()
                .statusCode(204)
                .body(emptyOrNullString())
                .time(lessThan(5000L))
                .log().body();
    }


}
