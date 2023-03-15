package com.example.bloodlink.model;

import android.widget.ImageView;
import android.widget.TextView;

public class PatientModel {

    private String title,
            author,
            location,
            postedOn,
            dueDate,
            description;

    private String userImage,patientImage;

    // User Image, Constructor
    public PatientModel(String title, String author, String location, String postedOn, String dueDate, String description, String userImage) {
        this.title = title;
        this.author = author;
        this.location = location;
        this.postedOn = postedOn;
        this.dueDate = dueDate;
        this.description = description;
        this.userImage = userImage;
    }

    // without Image, Constructor
    public PatientModel(String title, String author, String location, String postedOn, String dueDate, String description) {
        this.title = title;
        this.author = author;
        this.location = location;
        this.postedOn = postedOn;
        this.dueDate = dueDate;
        this.description = description;
        this.userImage = userImage;
        this.patientImage = patientImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPostedOn() {
        return postedOn;
    }

    public void setPostedOn(String postedOn) {
        this.postedOn = postedOn;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getPatientImage() {
        return patientImage;
    }

    public void setPatientImage(String patientImage) {
        this.patientImage = patientImage;
    }
}
