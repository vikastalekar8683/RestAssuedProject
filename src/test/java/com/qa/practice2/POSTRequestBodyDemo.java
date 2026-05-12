package com.qa.practice2;

import com.qa.day2.StudentPojo;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class POSTRequestBodyDemo {

    String studentId;

    // Start json server
    // D:\SeleniumAutomationProjects\RestAssuredProject\src\test\java\com\qa\day2>json-server --watch students.json
    // json-server students.json

    //1. Create POST request bodies using HashMap

    @Test
    public void createStudentUsingHashMap() {

        HashMap<String, Object> requestbody = new HashMap<>();
        requestbody.put("name", "vikas");
        requestbody.put("location", "pune");
        requestbody.put("phone", "1122334455");
        String courses[] = {"AI", "AI++"};
        requestbody.put("courses", courses);


        studentId = given()
                .contentType("application/json")
                .body(requestbody)
                .when()
                .post("http://localhost:3000/students/")
                .then()
                .statusCode(201)
                .header("Content-Type", "application/json")
                .body("name", equalTo("vikas"))
                .body("location", equalTo("pune"))
                .body("phone", equalTo("1122334455"))
                .body("courses[0]", equalTo("AI"))
                .body("courses[1]", equalTo("AI++"))
                .log().body()
                .extract().jsonPath().getString("id");
        ;
    }

    // 2.  Create POST Request body using org.json library
    @Test
    public void createStudentRequestUsingJSONLib() {
        JSONObject requestbody = new JSONObject();
        requestbody.put("name", "vikas");
        requestbody.put("location", "pune");
        requestbody.put("phone", "1122334455");
        String courses[] = {"AI", "AI++"};
        requestbody.put("courses", courses);

        System.out.println("Request Body : " + requestbody);

        studentId = given()
                .contentType("application/json")
                .body(requestbody.toString())
                .when()
                .post("http://localhost:3000/students/")
                .then()
                .statusCode(201)
                .header("Content-Type", "application/json")
                .body("name", equalTo("vikas"))
                .body("location", equalTo("pune"))
                .body("phone", equalTo("1122334455"))
                .body("courses[0]", equalTo("AI"))
                .body("courses[1]", equalTo("AI++"))
                .log().body()
                .extract().jsonPath().getString("id");
    }

    //3. Using POJO class
    @Test
    public void createStudentRequestUsingPOJOClass() {
        StudentPojo requestbody = new StudentPojo();
        requestbody.setName("vikas");
        requestbody.setLocation("Hadapsar");
        requestbody.setPhone("121212");
        String courses[] = {"AI", "AI++"};
        requestbody.setCourses(courses);

        given()
                .contentType("application/json")
                .body(requestbody)
                .when()
                .post("http://localhost:3000/students/")
                .then()
                .statusCode(201)
                .log().body();
    }


    //4. Using external Json File

    @Test
    public void createStudentUsingExternalFile() throws FileNotFoundException {
        File myFile = new File(".\\src\\test\\java\\com\\qa\\day2\\stud.json");
        FileReader fileReader = new FileReader(myFile);
        JSONTokener jsonTokener = new JSONTokener(fileReader);
        JSONObject requestBody = new JSONObject(jsonTokener);

        given()
                .contentType("application/json")
                .body(requestBody.toString())
                .when()
                .post("http://localhost:3000/students/")
                .then()
               // .body("student.name", equalTo("vikas"))
               // .body("location", equalTo("Hadapsar"))
               // .body("phone", equalTo("121212"))
                //.body("courses[0]", equalTo("AI")) // Corrected course[0]
                //.body("courses[1]", equalTo("C++")) // Corrected course[1]
                //.header("Content-Type", equalTo("application/json; charset=utf-8"))
                .log().all();

    }


}
