package com.qa.day2;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ParameterDemo {


    // Example of pass path parameter
    @Test
    public void pathparameterDemo() {
        given()
                .pathParam("country", "india") // Path parameter
                .when()
                .get("https://restcountries.com/v3.1/name/{country}")//https://restcountries.com/v3.1/name/india
                .then()
                .statusCode(200)
                .log().body();

    }


    // Example of pass query parameter
    @Test
    public void queryParam() {
        given()
                .header("x-api-key", "pro_c3894530b7fd01dfee7db6cfab2e48b8e5a2e479b0b5f98fcc4580a45c125027")
                .queryParam("project_id", "8775")
                .when()
                .get("https://reqres.in/api/collections") //https://reqres.in/api/collections?project_id=8775
                .then()
                .statusCode(200)
                .body("data[0].project_id", equalTo(8775))
                .log().body();
    }
}
