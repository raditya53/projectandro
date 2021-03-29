package com.example.fooddelivery;

public class DataUser {
    private String IdUser, Fullname, Email, Password, PhoneNumber;

    public DataUser(String idUser, String fullname, String email, String password, String phoneNumber) {
        IdUser = idUser;
        Fullname = fullname;
        Email = email;
        Password = password;
        PhoneNumber = phoneNumber;
    }

    public DataUser() {
    }

    public String getIdUser() {
        return IdUser;
    }

    public void setIdUser(String idUser) {
        IdUser = idUser;
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }
}
