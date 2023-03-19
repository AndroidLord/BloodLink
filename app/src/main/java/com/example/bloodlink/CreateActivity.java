package com.example.bloodlink;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bloodlink.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;
import java.util.Map;

public class CreateActivity extends AppCompatActivity {

    //DeclaringView's
    private EditText username,
    userpassword,
    useremail,
    userImage;

    private TextView loginText;

    private Button createAccountButton;

//DeclaringFirebaseUser
    private FirebaseUser currentUser;

    private FirebaseAuth firebaseAuth;

    private final FirebaseFirestore db=FirebaseFirestore.getInstance();
    private final CollectionReference userCollection = db.collection("UserCollections");




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        firebaseAuth = FirebaseAuth.getInstance();

        // ID's

        username = findViewById(R.id.userNameEditText_create);
        userpassword = findViewById(R.id.passwordEditText_create);
        useremail = findViewById(R.id.emailEditText_create);
        loginText = findViewById(R.id.loginTextView_create);
        createAccountButton = findViewById(R.id.createAccountButton_create);

        createAccountButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                createAccountWithEmailAndPassword();

            }
        });

        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateActivity.this,LoginActivity.class));
            }
        });

    }

    private void createAccountWithEmailAndPassword(){

        String name = username.getText().toString().trim();
        String email = useremail.getText().toString().trim();
        String password = userpassword.getText().toString().trim();

            if(!TextUtils.isEmpty(email)&&
            !TextUtils.isEmpty(password)&&
            !TextUtils.isEmpty(name)){

                Log.d("create", "createAccountWithEmailAndPassword: Its checking if any detail is missing");
                
            firebaseAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                Log.d("create","onComplete:Task is successful");

                                currentUser=firebaseAuth.getCurrentUser();

                                assert currentUser != null;
                                String id=currentUser.getUid();

                                Map<String,String> userObj=new HashMap<>();
                                userObj.put("userId",id);
                                userObj.put("userName",name);
                                userObj.put("userEmail",email);

                                userCollection.add(userObj).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {

                                        Log.d("create","onSuccess:adding Data to userCollection");
                                        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                                if(task.getResult().exists()){

                                                    String id = task.getResult().getString("userId");

                                                    UserModel userModel = UserModel.getInstance();
                                                    userModel.setUserId(id);
                                                    userModel.setUserName(task.getResult().getString("userName"));
                                                    userModel.setEmailId(task.getResult().getString("userEmail"));

                                                    Toast.makeText(CreateActivity.this, "*Account Created Successfully*", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(CreateActivity.this,DrawerActivity.class));
                                                    finish();
                                                }
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d("create", "onFailure: of Retriving the Document");
                                            }
                                        });
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("create", "onFailure: of Adding Data to userCollection");
                                    }
                                });

                            }
                            else{
                                Toast.makeText(CreateActivity.this,"ErrorCreatingAccount",Toast.LENGTH_SHORT).show();
                            }


                        }

                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }


                    });



            }

                    else {
                        Toast.makeText(this, "Please Fill The Details", Toast.LENGTH_SHORT).show();
                    }
};}