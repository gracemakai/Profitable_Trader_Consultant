package com.grace.profitabletraderconsultant;

public class User {

    public String phone;
    public String Key;

    public User() {
    }

    public User(String phone) {
        this.phone = phone;
    }

    public User(String phone, String key) {
        this.phone = phone;
        this.Key = key;
    }
}
