package com.daniel.final_project.objects;

import com.google.firebase.database.PropertyName;

public class User {
    private String uid = "";
    private String firstName = "";
    private String lastName = "";
    private String phone = "";
    private String email = "";
    private Boolean supplier = false;
    private Boolean isSignUp = false;

    public User() {
    }

    public String getUid() {
        return uid;
    }

    public User setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public User setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public Boolean getSupplier() {
        return supplier;
    }

    public User setSupplier(boolean supplier) {
        this.supplier = supplier;
        return this;
    }

    @PropertyName("isSignUp")
    public Boolean isSignUp() {
        return isSignUp;
    }

    @PropertyName("isSignUp")
    public User setSignUp(Boolean isSignUp) {
        this.isSignUp = isSignUp;
        return this;
    }
}
