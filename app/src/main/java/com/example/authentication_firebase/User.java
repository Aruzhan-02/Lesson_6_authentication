package com.example.authentication_firebase;

public class User {
    public String gender;
    public String fullName;
    public String email;
    public String phone;
    public String password;
    public String dormitory;
    public String faculties;

    public User() { }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String gender, String fullName, String email, String phone, String password, String dormitory, String faculties) {
        this.gender = gender;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.dormitory = dormitory;
        this.faculties = faculties;

    }

    public String getFaculties() {
        return faculties;
    }

    public void setFaculties(String faculties) {
        this.faculties = faculties;
    }
}
