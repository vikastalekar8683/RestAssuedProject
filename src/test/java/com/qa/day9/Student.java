package com.qa.day9;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Student {

    // Variables
    String name;
    String location;
    String phone;
    String[] courses;

    // Constructors
    public Student() {

    }

    // Parameterized Constructor
    public Student(String name, String location, String phone, String[] courses) {
        this.name = name;
        this.phone = phone;
        this.location = location;
        this.courses = courses;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String[] getCourses() {
        return courses;
    }

    public void setCourses(String[] courses) {
        this.courses = courses;
    }

    //Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(" ").append(location).append(" ").append(phone).append(" ");
        if (courses != null && courses.length > 0) {
            for (String course : courses) {
                sb.append(course).append(" ");
            }
        }
        return sb.toString();
    }
}
