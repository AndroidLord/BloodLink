package com.example.bloodlink.model;

import java.util.HashMap;

public class UserModel {



    private static UserModel instance;

    public static synchronized UserModel getInstance(){
        if(instance==null){
            instance = new UserModel();
        }
        return instance;
    }


    // User Details

    private String userId;
    private String userImage;
    private String userName;
    private String userPassword;
    private String emailId;

    // Later when the app works
    private String phoneNo;
    private String bloodGroup;
    private String country;
    private String State;
    private String Locality;
    private String sex;

    // first Integer denote No of Donation
    // Second one denote Time of last Donation
    private HashMap<Integer,Integer> bloodDonation;


    // Getters && Setters


    public static void setInstance(UserModel instance) {
        UserModel.instance = instance;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getLocality() {
        return Locality;
    }

    public void setLocality(String locality) {
        Locality = locality;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public HashMap<Integer, Integer> getBloodDonation() {
        return bloodDonation;
    }

    public void setBloodDonation(HashMap<Integer, Integer> bloodDonation) {
        this.bloodDonation = bloodDonation;
    }
}
