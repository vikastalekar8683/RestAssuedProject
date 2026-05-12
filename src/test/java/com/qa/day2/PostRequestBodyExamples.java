package com.qa.day2;


/*
    Demonstrate different ways to create POST request bodies:
    1. Using HashMap
    2. Using org.json Library
    3. Using POJO class
    4. Using external JSON file
 */


import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PostRequestBodyExamples {

    String studentId;

    // Start json server
    // D:\SeleniumAutomationProjects\RestAssuredProject\src\test\java\com\qa\day2>json-server --watch students.json
    // json-server students.json


    //1. Create POST request bodies using HashMap

     @Test
    public void createStudentUsingHashMap() {

        HashMap<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", "scott");
        requestBody.put("location", "France");
        requestBody.put("phone", "123456");

        String courses[] = {"C", "C++"};

        requestBody.put("courses", courses);


        studentId = given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("http://localhost:3000/students/")

                .then()
                .statusCode(201)
                .body("name", equalTo("scott"))
                .body("location", equalTo("France"))
                .body("phone", equalTo("123456"))
                .body("courses[0]", equalTo("C"))
                .body("courses[1]", equalTo("C++"))
                .header("Content-Type", "application/json")
                .log().body()
                .extract().jsonPath().getString("id");
    }


    // 2.  Create POST Request body using org.json library
    //  @Test
    public void createStudentUsingJsonLibrary() {
        JSONObject requestBody = new JSONObject();

        requestBody.put("name", "scott");
        requestBody.put("location", "France");
        requestBody.put("phone", "123456");

        String courses[] = {"C", "C++"};

        requestBody.put("courses", courses);


        studentId = given()
                .contentType("application/json")
                .body(requestBody.toString())
                .when()
                .post("http://localhost:3000/students/")

                .then()
                .statusCode(201)
                .body("name", equalTo("scott"))
                .body("location", equalTo("France"))
                .body("phone", equalTo("123456"))
                .body("courses[0]", equalTo("C"))
                .body("courses[1]", equalTo("C++"))
                .header("Content-Type", "application/json")
                .log().body()
                .extract().jsonPath().getString("id");
    }

    //3. Using POJO class
    //@Test
    public void createStudentUsingPojoClass() {
        StudentPojo requestBody = new StudentPojo();
        requestBody.setName("scott");
        requestBody.setLocation("France");
        requestBody.setLocation("123456");

        String courses[] = {"C", "C++"};

        requestBody.setCourses(courses);


        studentId = given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("http://localhost:3000/students/")

                .then()
                .statusCode(201)
                .body("name", equalTo(requestBody.getName()))
                .body("location", equalTo(requestBody.getLocation()))
                .body("phone", equalTo(requestBody.getPhone()))
                .body("courses[0]", equalTo(requestBody.getCourses()[0]))
                .body("courses[1]", equalTo(requestBody.getCourses()[1]))
                .header("Content-Type", "application/json")
                .log().body()
                .extract().jsonPath().getString("id");
    }

    //4. Using external Json File
    @Test
    public void createStudentUsingExternalFile() throws FileNotFoundException {
        File myFile = new File(".\\src\\test\\java\\com\\qa\\day2\\body.json");
        FileReader fileReader = new FileReader(myFile);
        JSONTokener jsonTokener = new JSONTokener(fileReader);
        JSONObject requestBody = new JSONObject(jsonTokener);


        studentId = given()
                .contentType("application/json")
                .body(requestBody.toString())
                .when()
                .post("http://localhost:3000/students/")

                .then()
                .statusCode(201)
                .body("name", equalTo("scott"))
                .body("location", equalTo("France"))
                .body("phone", equalTo("123456"))
                .body("courses[0]", equalTo("C"))
                .body("courses[1]", equalTo("C++"))
                .header("Content-Type", "application/json")
                .log().body()
                .extract().jsonPath().getString("id");
    }


    @AfterMethod
    public void deleteStudetRecord() {
        given()
                .when()
                .delete("http://localhost:3000/students/" + studentId)
                .then()
                .statusCode(200);
    }
}
