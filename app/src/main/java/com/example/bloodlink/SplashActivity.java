package com.example.bloodlink;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.bloodlink.model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class SplashActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;


    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference collectionReference = db.collection("UserCollections");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                currentUser = firebaseAuth.getCurrentUser();
                if (currentUser != null) {

                    collectionReference
                            .whereEqualTo("userId",firebaseAuth.getCurrentUser().getUid())
                            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                            if(error!=null){
                                Toast.makeText(SplashActivity.this, "Error Getting UserData", Toast.LENGTH_SHORT).show();
                                Log.d("splash", "onEvent: error: " + error.toString());
                            }
                            if(value!=null && !value.isEmpty()){
                                String id = value.getDocuments().get(0).getString("userId");
                                String name = value.getDocuments().get(0).getString("userName");
                                String email = value.getDocuments().get(0).getString("emailId");

                                UserModel userModel = UserModel.getInstance();
                                userModel.setUserName(name);
                                userModel.setEmailId(email);
                                userModel.setUserId(id);

                                    startActivity(new Intent(SplashActivity.this,DrawerActivity.class));
                                    finish();

                            }
                            else
                            {
                                Toast.makeText(SplashActivity.this, "User has Been Deleted", Toast.LENGTH_SHORT).show();
                                firebaseAuth.signOut();
                                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                                finish();
                            }

                        }
                    });

                }

                else {

                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
            }
        };

        int SPLASH_TIMING = 3000;


    }

    @Override
    protected void onStart() {
        super.onStart();

        currentUser = firebaseAuth.getCurrentUser();
        firebaseAuth.addAuthStateListener(authStateListener);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authStateListener != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }
}
