package com.qa.day2;

//POJO - Plain old JAVA Object
//This is class maintain data in the from of object.

public class StudentPojo {

    String name;
    String location;
    String phone;
    String courses[];


    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCourses(String[] courses) {
        this.courses = courses;
    }


    public String[] getCourses() {
        return courses;
    }

    public String getPhone() {
        return phone;
    }

    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

}
