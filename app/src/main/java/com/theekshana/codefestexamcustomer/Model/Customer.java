package com.theekshana.codefestexamcustomer.Model;

public class Customer {

    String Name;
    String Email;
    String Telephone;
    String NIc;
    String googleId;
    String fcmId;
    int status;

    public Customer() {
    }

    public Customer(String name, String email, String telephone, String NIc, String googleId, String fcmId, int status) {
        Name = name;
        Email = email;
        Telephone = telephone;
        this.NIc = NIc;
        this.googleId = googleId;
        this.fcmId = fcmId;
        this.status = status;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String telephone) {
        Telephone = telephone;
    }

    public String getNIc() {
        return NIc;
    }

    public void setNIc(String NIc) {
        this.NIc = NIc;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getFcmId() {
        return fcmId;
    }

    public void setFcmId(String fcmId) {
        this.fcmId = fcmId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
