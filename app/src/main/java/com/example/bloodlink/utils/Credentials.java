package com.example.bloodlink.utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Credentials {

    public static final String USER_COLLECTION = "UserCollections";
    public static final String PATIENT_COLLECTION = "PatientRequestCollection";

    private static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    public static FirebaseUser getCurrentUser(){
        return firebaseAuth.getCurrentUser();
    }


}
