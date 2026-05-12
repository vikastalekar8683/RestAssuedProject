package com.qa.day9;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

// Form java object from to JSON from called serialization
// Convert data JSON to JAVA Object call Deserialization

public class SerilizationandDeserilizationExample {

    String studId;

    @Test()
    public void testSerialization() {
        String courses[] = {"Selenium", "JAVA", "Python"};
        Student stud = new Student("John", "Dehli", "1234567890", courses);
        System.out.println("Stud : " + stud);



        studId = given()
                .contentType("application/json")
                .body(stud)  // Serialization happen
                .when()
                .post("http://localhost:3000/students")
                .then()
                .statusCode(201)
                .log().body()
                .extract().response().jsonPath().getString("id");
    }


    @Test(dependsOnMethods = "testSerialization")
    public void testDeserilization() {
        Response response = given()
                .pathParam("id", studId)
                .when()
                .get("http://localhost:3000/students/{id}")
                .then()
                .statusCode(200)
                .extract().response();

        //Extract "id" separately from JSON response
        String extracted_id = response.jsonPath().getString("id");

        //Deserialization - Convert JSON to Student object
        Student stu = response.as(Student.class);
        assertThat(stu.getName(), is("John"));
        assertThat(stu.getLocation(), is("Dehli"));
        assertThat(stu.getPhone(), is("1234567890"));
        assertThat(stu.getCourses()[0], is("Selenium"));
        assertThat(stu.getCourses()[1], is("JAVA"));
        assertThat(stu.getCourses()[2], is("Python"));

       // System.out.println("Student Details : " + stu +extracted_id);
    }

    @Test(dependsOnMethods = "testDeserilization")
    public void deleteStudent() {
        given()
                .pathParam("id", studId)
                .when()
                .delete("http://localhost:3000/students/{id}")
                .then()
                .statusCode(200)
                .log().body();
    }


}
