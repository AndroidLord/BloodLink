package com.example.bloodlink;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bloodlink.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;

public class UserDetailActivity extends AppCompatActivity {

    public static final int GALLERY_CODE = 01;
    private ImageView userImageView;
    private EditText userNameTextView,
            phoneNoTextView,
            emailTextView,
            ageTextView,
            bloodGroupTextView,
            genderTextView,
            descriptionTextView,
            countryEditText,
            stateEditText,
            cityEditText,
            postalCodeEditText;
    private MaterialButton saveUserButton;

    private Uri imageUri;
    private final UserModel userModel = UserModel.getInstance();

    ActivityResultLauncher<String> mgetContent;

    // Firebase
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;
    Toolbar toolbar;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference storageReference;

    private CollectionReference collectionReference = db.collection("UserCollections");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);



        //ImageView
        userImageView = findViewById(R.id.userImageView_UserDetail);
        //EditText
        userNameTextView = findViewById(R.id.userNameEditText_UserDetail);
        phoneNoTextView = findViewById(R.id.phoneNoEditText_UserDetail);
        emailTextView = findViewById(R.id.emailEditText_UserDetail);
        ageTextView = findViewById(R.id.ageEditText_UserDetail);
        genderTextView = findViewById(R.id.genderEditText_UserDetail);
        bloodGroupTextView = findViewById(R.id.bloodGroupEditText_UserDetail);
        descriptionTextView = findViewById(R.id.userDescriptionEditText_UserDetail);
        countryEditText = findViewById(R.id.countryEditText_UserDetail);
        stateEditText = findViewById(R.id.stateEditText_UserDetail);
        cityEditText = findViewById(R.id.cityEditText_UserDetail);
        postalCodeEditText = findViewById(R.id.postalCodeEditText_UserDetail);
        //Button
        saveUserButton = findViewById(R.id.updateButton_UserDetail);

        toolbar = findViewById(R.id.toolbar_UserDetail);
        setSupportActionBar(toolbar);

        // Setting Up Firebase
        storageReference = FirebaseStorage.getInstance().getReference();

        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                currentUser = firebaseAuth.getCurrentUser();
                if(currentUser!=null){
                    // user is there

                }
                else{
                    // user is null

                }
            }
        };

        userImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mgetContent.launch("image/*");
            }
        });

        mgetContent = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                Intent intent = new Intent(UserDetailActivity.this,CropperActivity.class);
                intent.putExtra("DATA",result.toString()+"");
                Log.d("main", "starting Activity");
                startActivityForResult(intent,101);
            }
        });


        userNameTextView.setText(userModel.getUserName());
        emailTextView.setText(userModel.getEmailId());


        saveUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserData();
            }
        });

    }

    private void saveUserData() {


        String name = userNameTextView.getText().toString().trim();
        String email = emailTextView.getText().toString().trim();

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email)) {

            Log.d("UserDetail", "updatingUserData: Before the Update the UserModelL " + userModel.toString());
            gettingValueFromViews(name,email);
            Log.d("UserDetail", "updatingUserData: After the Update the UserModelL " + userModel.toString());

            savingUserModelToFirebase();


        }
    }

    private void savingUserModelToFirebase() {

        if(imageUri!=null){
            StorageReference filepath = storageReference
                    .child("user_images")
                    .child(currentUser.getUid());


            filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Toast.makeText(UserDetailActivity.this, "User Image Saved", Toast.LENGTH_SHORT).show();

                    filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            // Setting up user image
                            userModel.setUserImage(uri.toString());
                            Log.d("UserDetail", "updatingUserData: After Updating UserImage " + userModel.toString());

                            UpdatingUser();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("userdetail", "onFailure: " + e.toString());
                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("userdetail", "onFailure: Putting Image failed " + e.toString());
                }
            });
        }
        else{
            UpdatingUser();
        }

    }

    private void UpdatingUser() {
        collectionReference
                .whereEqualTo("userId",currentUser.getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);

                        documentSnapshot.getReference().update(userModel.toMap()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                Toast.makeText(UserDetailActivity.this, "User Updated", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(UserDetailActivity.this,DrawerActivity.class));
                                finish();
                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    private void gettingValueFromViews(String name, String email) {

        String phoneNo = phoneNoTextView.getText().toString().trim();
        String age = ageTextView.getText().toString().trim();
        String bloodGroup = bloodGroupTextView.getText().toString().trim();
        String gender = genderTextView.getText().toString().trim();
        String country = countryEditText.getText().toString().trim();
        String state = stateEditText.getText().toString().trim();
        String city = cityEditText.getText().toString().trim();
        String postalCode = postalCodeEditText.getText().toString().trim();
        String userDescription = descriptionTextView.getText().toString().trim();

        if(!name.isEmpty()) userModel.setUserName(name);
        if(!phoneNo.isEmpty()) userModel.setPhoneNo(phoneNo);
        if(!email.isEmpty()) userModel.setEmailId(email);
        if(!bloodGroup.isEmpty()) userModel.setBloodGroup(bloodGroup);
        if(!gender.isEmpty()) userModel.setGender(gender);
        if(!age.isEmpty()) userModel.setAge(age);
        if(!country.isEmpty()) userModel.setCountry(country);
        if(!state.isEmpty()) userModel.setState(state);
        if(!city.isEmpty()) userModel.setCity(city);
        if(!postalCode.isEmpty()) userModel.setPostalCode(postalCode);
        if(!userDescription.isEmpty()) userModel.setUserDescription(userDescription);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("main", "Inside the Main onActivityCode");

        if(resultCode==-1 && requestCode==101){

            String result = data.getStringExtra("RESULT");

            Log.d("main", "onActivityResult: Main The Uri Data is: " + result);
            Uri resultUri = null;
            if(result!=null){
                resultUri = Uri.parse(result);
                imageUri = resultUri;
            }
            userImageView.setImageURI(resultUri);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.userdetailmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.skip_UserDetail:
            case R.id.skipIcon_UserDetail:
            case R.id.skipText_UserDetail:
                Toast.makeText(this, "Skipping this page", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(UserDetailActivity.this,DrawerActivity.class));
                break;


        }

        return super.onOptionsItemSelected(item);
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

        firebaseAuth.removeAuthStateListener(authStateListener);
    }
}

