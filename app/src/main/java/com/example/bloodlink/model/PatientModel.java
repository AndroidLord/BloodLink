package com.example.bloodlink.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class PatientModel implements Parcelable {

    private static PatientModel instance;

    private PatientModel() {

    }

    protected PatientModel(Parcel in) {
        patientName = in.readString();
        gender = in.readString();
        severity = in.readString();
        email = in.readString();
        phoneNo = in.readString();
        bloodGroup = in.readString();
        address = in.readString();
        description = in.readString();
        age = in.readString();
        relationToPatient = in.readString();
        patientImage = in.readString();
        userName = in.readString();
        userImage = in.readString();
        dueDate = in.readLong();
        PostedOn = in.readLong();
    }

    public static final Creator<PatientModel> CREATOR = new Creator<PatientModel>() {
        @Override
        public PatientModel createFromParcel(Parcel in) {
            return new PatientModel(in);
        }

        @Override
        public PatientModel[] newArray(int size) {
            return new PatientModel[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(patientName);
        dest.writeString(gender);
        dest.writeString(severity);
        dest.writeString(email);
        dest.writeString(phoneNo);
        dest.writeString(bloodGroup);
        dest.writeString(address);
        dest.writeString(description);
        dest.writeString(age);
        dest.writeString(relationToPatient);
        dest.writeString(patientImage);
        dest.writeString(userName);
        dest.writeString(userImage);
        dest.writeLong(dueDate);
        dest.writeLong(PostedOn);
    }
}


