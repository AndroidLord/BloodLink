package com.example.bloodlink.model;

import android.widget.ImageView;
import android.widget.TextView;

public class PatientModel {

    private static PatientModel instance;

    private PatientModel() {

    }
    public static PatientModel getInstance(){
        if (instance==null){
            instance = new PatientModel();
        }
        return instance;
    }

    private String patientName,
    gender,
    severity,
    email,
    phoneNo,
    bloodGroup,
    address,
    description,
    age,
    relationToPatient,
    patientImage,
            userName,userImage;

    private long dueDate,PostedOn;


    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getRelationToPatient() {
        return relationToPatient;
    }

    public void setRelationToPatient(String relationToPatient) {
        this.relationToPatient = relationToPatient;
    }

    public String getPatientImage() {
        return patientImage;
    }

    public void setPatientImage(String patientImage) {
        this.patientImage = patientImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public long getDueDate() {
        return dueDate;
    }

    public void setDueDate(long dueDate) {
        this.dueDate = dueDate;
    }

    public long getPostedOn() {
        return PostedOn;
    }

    public void setPostedOn(long postedOn) {
        PostedOn = postedOn;
    }
}


