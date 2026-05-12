package com.qa.practice2;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class PathParamDemo {

    @Test(priority = 1)
    public void pathParameterDemo(){
        given()
                .pathParams("country","india")
                .when()
                .get("https://restcountries.com/v3.1/name/{country}")
                .then()
                .statusCode(200)
                .time(lessThan(5000L))
                .log().body();
    }

    @Test
    public void queryParamDemo(){
        given()
                .header("x-api-key", "pro_c3894530b7fd01dfee7db6cfab2e48b8e5a2e479b0b5f98fcc4580a45c125027")
                .queryParam("project_id", "8775")
                .when()
                .get("https://reqres.in/api/collections")
                .then()
                .statusCode(200)
                .time(lessThan(5000L))
                .log().body();
    }

}
