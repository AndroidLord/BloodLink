package com.example.bloodlink.model;

import android.widget.ImageView;
import android.widget.TextView;

public class PatientModel {


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
    patientImage;

    private String dueDate,PostedOn,userName,userImage;

    public PatientModel() {
    }

    public PatientModel(String patientName, String gender, String severity, String email, String phoneNo, String bloodGroup, String address, String description, String age, String relationToPatient, String patientImage, String dueDate, String postedOn, String userName, String userImage) {
        this.patientName = patientName;
        this.gender = gender;
        this.severity = severity;
        this.email = email;
        this.phoneNo = phoneNo;
        this.bloodGroup = bloodGroup;
        this.address = address;
        this.description = description;
        this.age = age;
        this.relationToPatient = relationToPatient;
        this.patientImage = patientImage;
        this.dueDate = dueDate;
        PostedOn = postedOn;
        this.userName = userName;
        this.userImage = userImage;
    }

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

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getPostedOn() {
        return PostedOn;
    }

    public void setPostedOn(String postedOn) {
        PostedOn = postedOn;
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


}
