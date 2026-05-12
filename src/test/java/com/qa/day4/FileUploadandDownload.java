package com.qa.day4;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

import java.io.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertTrue;

public class FileUploadandDownload {

    //1. Single file upload

    //@Test
    public void uploadSingleFile() {
        File myFile = new File(
                "C:\\Users\\Vikas.T\\Downloads\\UloadFileRestAPI\\Test1.txt");

        given()
                .multiPart("file", myFile, "text/plain")
                .contentType("multipart/form-data")
                .when()
                .post("http://localhost:8080/uploadFile")
                .then()
                .statusCode(200)
                .body("fileName", equalTo("Test1.txt"))
                .log().body();
    }

    // Upload multiple Files
   // @Test
    public void uploadMultipleFiles() {
        File myFile1 = new File(
                "C:\\Users\\Vikas.T\\Downloads\\UloadFileRestAPI\\Test1.txt");
        File myFile2 = new File(
                "C:\\Users\\Vikas.T\\Downloads\\UloadFileRestAPI\\Test2.txt");

        given()
                .multiPart("files", myFile1, "text/plain")
                .multiPart("files", myFile2, "text/plain")

                .contentType("multipart/form-data")

                .when()
                .post("http://localhost:8080/uploadMultipleFiles")

                .then()
                .statusCode(200)
                .body("[0].fileName", equalTo("Test1.txt"))
                .body("[1].fileName", equalTo("Test2.txt"))
                .log().body();
    }

    @Test
    public void downloadFile() throws IOException {
        RestAssured.baseURI = "http://localhost:8080";


                given()
                        .pathParam("filename", "Test1.txt")
                        .when()
                        .get("downloadFile/{filename}")
                        .then()
                        .statusCode(200)
                        .log().body();

    }


    @Test
    public void upload_File(){
        File myFile = new File("C:\\Users\\Vikas.T\\Downloads\\Test1.txt");

        given()
                .multiPart("files", myFile, "text/plain")
                .when()
                .post("http://the-internet.herokapp.com/upload")
                .then()
                .statusCode(200)

                .log().body();
    }




}
