package com.example.bloodlink;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bloodlink.model.UserModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {

    private Button loginButton;

    private EditText username,
            userpassword,
            useremail;
    private TextView signUpText;


    // Setting Up Firebase
    private FirebaseUser currentUser;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("UserCollections");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // ID's
        loginButton = findViewById(R.id.loginAccountButton_login);
        signUpText = findViewById(R.id.signupText);
        //username = findViewById(R.id.userNameEditText_login);
        useremail = findViewById(R.id.emailEditText_login);
        userpassword = findViewById(R.id.passwordEditText_login);

        // Initiating Firebase
        firebaseAuth = FirebaseAuth.getInstance();


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               // String name = username.getText().toString().trim();
                String email = useremail.getText().toString().trim();
                String password = userpassword.getText().toString().trim();

                if (!TextUtils.isEmpty(email) &&
                    !TextUtils.isEmpty(password)) {

                    loginAccountUsingEmailPassword(email, password);

                }
                else {

                    if(email.isEmpty() && password.isEmpty()){
                        Toast.makeText(LoginActivity.this, "Please Enter Details", Toast.LENGTH_SHORT).show();
                    }
                    else if (email.isEmpty()) {
                        Toast.makeText(LoginActivity.this, "Please Enter Your Email", Toast.LENGTH_SHORT).show();
                        useremail.requestFocus();
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Please Enter Your Password", Toast.LENGTH_SHORT).show();
                        userpassword.requestFocus();
                    }

                }


                //startActivity(new Intent(LoginActivity.this, DrawerActivity.class));

            }
        });

        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, CreateActivity.class));
            }
        });

    }

    private void loginAccountUsingEmailPassword(String email, String password) {

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                currentUser = authResult.getUser();

                String tempCurrentUserId = currentUser.getUid();

                collectionReference
                        .whereEqualTo("userId", tempCurrentUserId)
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                                if (error != null) {
                                    Toast.makeText(LoginActivity.this, "There is an error", Toast.LENGTH_SHORT).show();
                                    Log.d("login", "onEvent: There is an error: " + error);
                                    return;
                                }

                                if (value != null && !value.isEmpty()) {

                                    Toast.makeText(LoginActivity.this, "LogIn Successful", Toast.LENGTH_SHORT).show();

                                    for (QueryDocumentSnapshot documentSnapshot : value) {

                                        UserModel userModel = UserModel.getInstance();
                                        userModel.setUserId(currentUser.getUid());
                                        userModel.setUserName(documentSnapshot.getString("userName"));
                                        userModel.setEmailId(email);

                                        startActivity(new Intent(LoginActivity.this, DrawerActivity.class));
                                        finish();
                                    }


                                } else {
                                    Toast.makeText(LoginActivity.this, "No Account Found!", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(LoginActivity.this, "Failed To Sign In", Toast.LENGTH_SHORT).show();

            }
        });


    }


}