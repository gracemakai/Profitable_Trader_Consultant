package com.grace.profitabletraderconsultant;

public class User {

    public String phone;
    private String UID;

    public User() {
    }

    public User(String phone) {
        this.phone = phone;
    }

    public User(String phone, String UID) {
        this.phone = phone;
        this.UID = UID;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }
}
