package com.qa.practice.day5Practice;

import io.restassured.path.json.JsonPath;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ParsingComplexJSONResp {

        /*
        1. Verify the status field in the JSON response is "Success".
        2. Validate id, name and Email fields of the user
        3. Confirm the first phone number is of the type home with the value  123-456-7890
        4. Verify geo coordinates of the user's address are latitude  39.7817 and longitude -89.6501
        5. Validate that user has enabled notifications and is using the "dark" theme.
     */

    JSONObject getJSONResponse() {

        File myFile = new File(".\\src\\TestData\\complex.json");
        FileReader fileReader = null;

        try {
            fileReader = new FileReader(myFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        JSONTokener jsonTokener = new JSONTokener(fileReader);
        JSONObject jsonResponse = new JSONObject(jsonTokener);

        return jsonResponse;
    }

    // 1. Verify the status field in the JSON response is "Success".
    @Test
    public void testUserDeatailsValidation(){
        String str = getJSONResponse().toString();
        JsonPath jsonPath = new JsonPath(str);
        String staus = jsonPath.getString("status");
        System.out.println("Status : " + staus);
        assertThat(staus, is("success"));
    }

    //2. Validate id, name and Email fields of the user

    @Test
    public void validate_User_name_id_email_fields(){
        String str = getJSONResponse().toString();
        JsonPath jsonPath = new JsonPath(str);

        //Validate User id
        int userid = jsonPath.getInt("data.users[0].userDetails.id");
        System.out.println("iserid : " + userid);
        assertThat(userid, is(12345));

        //3. Confirm the first phone number is of the type home with the value  123-456-7890
        String phoneNumber = jsonPath.getString("data.users[0].userDetails.phoneNumbers[0].number");
        System.out.println("Phone Number : " + phoneNumber);
        assertThat(phoneNumber, is("123-456-7890"));

        //4. Verify geo coordinates of the user's address are latitude  39.7817 and longitude -89.6501
        double lat = jsonPath.getDouble("data.users[0].address.geo.latitude");
        double longitude = jsonPath.getDouble("data.users[0].address.geo.longitude");

        assertThat(lat, is(39.7817));
        assertThat(longitude, is(-89.6501));

        //5. Validate that user has enabled notifications and is using the "dark" theme.
        boolean notification = jsonPath.getBoolean("data.users[0].preferences.notifications");
        assertThat(notification, is(true));
        String theme = jsonPath.getString("data.users[0].preferences.theme");
        assertThat(theme, is("dark"));

    }





   /* public static void main(String[] args) {
      *//*  ParsingComplexJSONResp obj = new ParsingComplexJSONResp();
        String str = obj.getJSONResponse().toString();
        System.out.println(str);
*//*

        File file1 = new File(".\\src\\TestData\\complex.json");
        FileReader fileReader;
        try {
            fileReader = new FileReader(file1);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        JSONTokener jsonTokener = new JSONTokener(fileReader);
        JSONObject jsonObject = new JSONObject(jsonTokener);
        System.out.println(jsonObject.toString());


    }*/


}
