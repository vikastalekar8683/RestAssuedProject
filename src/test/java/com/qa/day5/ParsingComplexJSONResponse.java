package com.qa.day5;

import io.restassured.path.json.JsonPath;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class ParsingComplexJSONResponse {

    /*
        1. Verify the status field in the JSON response is "Success".
        2. Validate id, name and Email fields of the user
        3. Confirm the first phone number is of the type home with the value  123-456-7890
        4. Verify geo coordinates of the user's address are latitude  39.7817 and longitude -89.6501
        5. Validate that user has enabled notifications and is using the "dark" theme.
     */


    // User defined method - this will read data from JSON, convert to json response
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

    //1. Verify the status field in the JSON response is "Success".
    @Test
    public void testUserDeatailsValidation() {

        // Converting JSOn object to JSON Path
        String str = getJSONResponse().toString();
        JsonPath jsonPath = new JsonPath(str);

        // a. Verify status

        String status = jsonPath.getString("status");
        System.out.println(status);
        assertThat(status, is("success"));

        // b. Validate user details
        int id = jsonPath.getInt("data.users[0].userDetails.id");
        String name = jsonPath.getString("data.users[0].userDetails.name");
        String email = jsonPath.getString("data.users[0].userDetails.email");
        assertThat(id, is(12345));
        assertThat(name, is("John Doe"));
        assertThat(email, is("john.doe@example.com"));

        // c Validate home phone number
        String homePhoneType = jsonPath.getString("data.users[0].userDetails.phoneNumbers[0].type");
        String homePhone = jsonPath.getString("data.users[0].userDetails.phoneNumbers[0].number");
        assertThat(homePhoneType, is("home"));
        assertThat(homePhone, is("123-456-7890"));

        //data.users[1].address.geo.latitude
        //data.users[1].address.geo.longitude

        // d. Validate geo Coordinate
        double latitude = jsonPath.getDouble("data.users[0].address.geo.latitude");
        double longitude = jsonPath.getDouble("data.users[0].address.geo.longitude");

        assertThat(latitude, is(39.7817));
        assertThat(longitude, is(-89.6501));

        //data.users[1].preferences.theme
        //data.users[1].preferences.notifications

        // Validating preferences
        boolean notification = jsonPath.getBoolean("data.users[1].preferences.notifications");
        String theme = jsonPath.getString("data.users[1].preferences.theme");
        assertThat(notification, is(false));
        assertThat(theme, is("light"));
    }

}
