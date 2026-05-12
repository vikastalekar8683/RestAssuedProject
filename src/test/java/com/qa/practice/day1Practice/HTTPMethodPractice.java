package com.qa.practice.day1Practice;
import org.testng.annotations.Test;
import java.util.HashMap;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class HTTPMethodPractice {

    int userId;

    @Test(priority = 1)
    public void getUsers_p() {
        given()
                .header("x-api-key", "reqres_57146a59c8e84d1e9893ba155cb5154a")
                .when()
                .get("https://reqres.in/api/users")
                .then()
                .statusCode(200)
                .header("content-Type", equalTo("application/json; charset=utf-8"))
                .body("page", equalTo(1))
                .body(containsString("email"))
                .time(lessThan(5000L))
                .log().all();
    }


    @Test(priority = 2)
    public void createuser_p() {
        HashMap<String, String> hp = new HashMap<String, String>();
        hp.put("name", "Avira");
        hp.put("job", "CTO");
        userId = given()
                .header("x-api-key", "reqres_57146a59c8e84d1e9893ba155cb5154a")
                .contentType("application/json")
                .body(hp)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .statusCode(201)
                .header("content-Type", equalTo("application/json; charset=utf-8"))
                .time(lessThan(5000L))
                .body("name", equalToIgnoringCase("avira"))
                .body("job", equalToIgnoringCase("cto"))
                .log().all()
                .extract().jsonPath().getInt("id");

        System.out.println("UserID : " + userId);
    }

    @Test(priority = 3, dependsOnMethods = "createuser_p")
    public void getUsersByID_p() {
        given()
                .header("x-api-key", "reqres_57146a59c8e84d1e9893ba155cb5154a")
                .when()
                .get("https://reqres.in/api/users/1")
                .then()
                .statusCode(200)
                .header("content-Type", equalTo("application/json; charset=utf-8"))
                .time(lessThan(5000L))
                .log().all();
    }

    @Test(priority = 4, dependsOnMethods = "createuser_p")
    public void update_user_p() {
        HashMap<String, String> hp = new HashMap<String, String>();
        hp.put("name", "Avira_Update");
        hp.put("job", "CTO_Update");
        given()
                .header("x-api-key", "reqres_57146a59c8e84d1e9893ba155cb5154a")
                .contentType("application/json")
                .body(hp)
                .when()
                .put("https://reqres.in/api/users/" + userId)
                .then()
                .statusCode(200)
                .header("content-Type", equalTo("application/json; charset=utf-8"))
                .time(lessThan(5000L))
                .body("name", equalToIgnoringCase("Avira_Update"))
                .body("job", equalToIgnoringCase("CTO_Update"))
                .log().all();
    }

    @Test(priority = 5, dependsOnMethods = {"createuser_p", "update_user_p"})
    public void delete_User_p() {
        given()
                .header("x-api-key", "reqres_57146a59c8e84d1e9893ba155cb5154a")
                .contentType("application/json")
                .when()
                .delete("https://reqres.in/api/users/" + userId)
                .then()
                .statusCode(204)
                .time(lessThan(5000L))
                .body(emptyOrNullString())
                .log().all();
    }
}
