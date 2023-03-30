package com.example.bloodlink.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bloodlink.CropperActivity;
import com.example.bloodlink.R;
import com.example.bloodlink.UserDetailActivity;
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
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProfileFragment extends Fragment {

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
    private MaterialButton updateButton;

    ActivityResultLauncher<String> mgetContent;
    private Uri imageUri;


    // Globalising Firebase
    private FirebaseUser currentUser;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference collectionReference = db.collection("UserCollections");
    private StorageReference storageReference;
    UserModel userModel = UserModel.getInstance();


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        userImageView = view.findViewById(R.id.userImageView_Profile);
        userNameTextView = view.findViewById(R.id.userNameEditText_Profile);
        phoneNoTextView = view.findViewById(R.id.phoneNoEditText_Profile);
        emailTextView = view.findViewById(R.id.emailEditText_Profile);
        ageTextView = view.findViewById(R.id.ageEditText_Profile);
        genderTextView = view.findViewById(R.id.genderEditText_Profile);
        bloodGroupTextView = view.findViewById(R.id.bloodGroupEditText_Profile);
        descriptionTextView = view.findViewById(R.id.userDescriptionEditText_Profile);
        updateButton = view.findViewById(R.id.updateButton_Profile);
        countryEditText = view.findViewById(R.id.countryEditText_Profile);
        stateEditText = view.findViewById(R.id.stateEditText_Profile);
        cityEditText = view.findViewById(R.id.cityEditText_Profile);
        postalCodeEditText = view.findViewById(R.id.postalCodeEditText_Profile);

        firebaseAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
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

        settingValuesToViews();

        userImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mgetContent.launch("image/*");
            }
        });
        mgetContent = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                Intent intent = new Intent(getContext(), CropperActivity.class);
                intent.putExtra("DATA",result.toString()+"");
                Log.d("profile", "onActivityResult when userImage is clicked");
                startActivityForResult(intent,101);
            }
        });
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatingUserData();
            }
        });

        return view;
    }

    private void updatingUserData() {

        String name = userNameTextView.getText().toString().trim();
        String email = emailTextView.getText().toString().trim();

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email)) {

            //settingValuesToViews();
            Log.d("profile", "updatingUserData: Before the Update the UserModelL " + userModel.toString());
            gettingValueFromViews();
            updatingUserModelToFirebase();
            Log.d("profile", "updatingUserData: After the Update the UserModelL " + userModel.toString());
        }

    }

    private void updatingUserModelToFirebase() {

        if(imageUri!=null){

            Log.d("profile", "updatingUserModelToFirebase: Image Uri is not null");

            StorageReference filepath = storageReference
                    .child("user_images")
                    .child(currentUser.getUid());

            filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Toast.makeText(getContext(), "User Image saved", Toast.LENGTH_SHORT).show();

                    filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            userModel.setUserImage(uri.toString());
                            Log.d("UserDetail", "updatingUserData: After Updating UserImage " + userModel.toString());

                            updatingUser();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // if getting the url of photo is failed
                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // if uploading the url fails
                }
            });

        }
        else{

            Log.d("profile", "updatingUserModelToFirebase: Image Uri is null");

            updatingUser();
        }

    }

    private void updatingUser() {
        collectionReference
                .whereEqualTo("userId", UserModel.getInstance().getUserId())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {


                            Log.d("profile", "onComplete: UserId: " + userModel.toString());

                            if (!task.getResult().getDocuments().isEmpty()) {

                                DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);

                                String docId = documentSnapshot.getId();

                                db.collection("UserCollections")
                                        .document(docId)
                                        .update(userModel.toMap())
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {

                                                Toast.makeText(getContext(), "Account Updated", Toast.LENGTH_SHORT).show();

                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getContext(), "Updated Failed", Toast.LENGTH_SHORT).show();
                                            }
                                        });


                            } else {
                                Toast.makeText(getContext(), "No ID Found", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(getContext(), "Data Retrieval Failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void settingValuesToViews() {
            currentUser = firebaseAuth.getCurrentUser();
        collectionReference
                .whereEqualTo("userId",currentUser.getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                if (!documents.isEmpty()) {
                    UserModel userModel = documents.get(0).toObject(UserModel.class);
                    // do something with userModel
                    Log.d("profile", "onSuccess: Using Firebase data: " + userModel.toString());
                    //userImage
                    assert userModel != null;
                    Picasso.get()
                            .load(userModel.getUserImage())
                            .placeholder(R.drawable.user)
                            .into(userImageView);
                    //EditText
                    userNameTextView.setText(userModel.getUserName());
                    phoneNoTextView.setText(userModel.getPhoneNo());
                    emailTextView.setText(userModel.getEmailId());
                    bloodGroupTextView.setText(userModel.getBloodGroup());
                    genderTextView.setText(userModel.getGender());
                    ageTextView.setText(userModel.getAge());
                    descriptionTextView.setText(userModel.getUserDescription());
                    countryEditText.setText(userModel.getCountry());
                    stateEditText.setText(userModel.getState());
                    cityEditText.setText(userModel.getCity());
                    postalCodeEditText.setText(userModel.getPostalCode());


                }
                else {
                    // handle the case where no documents are returned
                    UserModel userModel = UserModel.getInstance();

                    Log.d("profile", "onSuccess: Using local data: " + userModel.toString());
                    userImageView.setImageURI(Uri.parse(userModel.getUserImage()));
                    //EditText
                    userNameTextView.setText(userModel.getUserName());
                    phoneNoTextView.setText(userModel.getPhoneNo());
                    emailTextView.setText(userModel.getEmailId());
                    bloodGroupTextView.setText(userModel.getBloodGroup());
                    genderTextView.setText(userModel.getGender());
                    ageTextView.setText(userModel.getAge());
                    descriptionTextView.setText(userModel.getUserDescription());
                    countryEditText.setText(userModel.getCountry());
                    stateEditText.setText(userModel.getState());
                    cityEditText.setText(userModel.getCity());
                    postalCodeEditText.setText(userModel.getPostalCode());
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }

    private void gettingValueFromViews() {

        String name = userNameTextView.getText().toString().trim();
        String email = emailTextView.getText().toString().trim();
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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
    public void onStart() {
        super.onStart();

        currentUser = firebaseAuth.getCurrentUser();
        firebaseAuth.addAuthStateListener(authStateListener);

    }



    @Override
    public void onStop() {
        super.onStop();

        firebaseAuth.removeAuthStateListener(authStateListener);
    }
}

