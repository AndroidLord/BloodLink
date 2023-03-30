package com.example.bloodlink.model;

import java.util.HashMap;
import java.util.Map;

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

    private String userName;
    private String emailId;

    // Later when the app works
    private String userImage;
    private String phoneNo;
    private String bloodGroup;
    private String country;
    private String state;
    private String city;
    private String postalCode;
    private String gender;
    private String age;
    private String userDescription;

    // first Integer denote No of Donation
    // Second one denote Time of last Donation
    private HashMap<Integer,Integer> bloodDonation;

    public Map<String, Object> toMap() {

        Map<String, Object> map = new HashMap<>();

        map.put("userName", this.userName);
        map.put("emailId", this.emailId);

        if(phoneNo!=null) map.put("phoneNo", this.phoneNo);

        if(bloodGroup!=null) map.put("bloodGroup", this.bloodGroup);

        if(gender!=null) map.put("gender", this.gender);

        if(userImage!=null) map.put("userImage", this.userImage);

        if(age!=null) map.put("age", this.age);

        if(country!=null) map.put("country", this.country);

        if(state !=null) map.put("state", this.state);

        if(city!=null) map.put("city", this.city);

        if(postalCode !=null) map.put("postalCode", this.postalCode);

        if(userDescription!=null) map.put("userDescription", this.userDescription);

        return map;

    }


    public UserModel() {
    }

    // Constructors

    public UserModel(String userId, String userName, String emailId, String userImage, String phoneNo, String bloodGroup, String country, String state, String city, String postalCode, String gender, String age, String userDescription) {
        this.userId = userId;
        this.userName = userName;
        this.emailId = emailId;
        this.userImage = userImage;
        this.phoneNo = phoneNo;
        this.bloodGroup = bloodGroup;
        this.country = country;
        this.state = state;
        this.city = city;
        this.postalCode = postalCode;
        this.gender = gender;
        this.age = age;
        this.userDescription = userDescription;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", emailId='" + emailId + '\'' +
                ", userImage='" + userImage + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", bloodGroup='" + bloodGroup + '\'' +
                ", country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                ", PostalCode='" + postalCode + '\'' +
                ", gender='" + gender + '\'' +
                ", age='" + age + '\'' +
                ", userDescription='" + userDescription + '\'' +
                ", bloodDonation=" + bloodDonation +
                '}';
    }

    // Getters && Setters


    public String getUserDescription() {
        return userDescription;
    }

    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
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
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public HashMap<Integer, Integer> getBloodDonation() {
        return bloodDonation;
    }

    public void setBloodDonation(HashMap<Integer, Integer> bloodDonation) {
        this.bloodDonation = bloodDonation;
    }
}
