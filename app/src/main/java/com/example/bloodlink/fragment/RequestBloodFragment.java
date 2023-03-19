package com.example.bloodlink.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bloodlink.R;
import com.example.bloodlink.model.PatientModel;
import com.example.bloodlink.model.UserModel;
import com.google.android.gms.common.util.DataUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RequestBloodFragment extends Fragment implements View.OnClickListener {

    private ImageButton button;
    private TextView genderTextView;

    private EditText firstNameEditText,lastNameEditText,
            severityEditText,
            emailEditText,
            phoneNoEditText,
            bloodGroupEditText,
            addressEditText,
            descriptionEditText,
            ageEditText,
            relationToPatientEditText,
            patientImage;
    private String spinnerText, dueDateStr,patientName;
    private Long dueDateLong,postedOnLong;
    private CalendarView calendarView;

    private Button savebutton;

    PatientModel patientModel;

    // Setting Up Firebase
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference patientRequestCollection = db.collection("PatientRequestCollection");

    public RequestBloodFragment() {

        patientModel = PatientModel.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_request_blood, container, false);


        // ID's
        Spinner spinner = view.findViewById(R.id.spinner);
        genderTextView = view.findViewById(R.id.gender_RequestBlood_TextView);//TextView
        firstNameEditText = view.findViewById(R.id.firstName_RequestBlood_EditText);
        lastNameEditText = view.findViewById(R.id.lastname_RequestBlood_EditText);
        phoneNoEditText = view.findViewById(R.id.phone_bloodRequest);
        emailEditText = view.findViewById(R.id.email_bloodRequest);
        addressEditText = view.findViewById(R.id.address_BloodRequest);
        ageEditText = view.findViewById(R.id.age_BloodRequest);
        descriptionEditText = view.findViewById(R.id.description_BloodRequest);
        savebutton = view.findViewById(R.id.saveButton);

        GenderBottomSheetFragment bottomSheetFragment = new GenderBottomSheetFragment();

        //Setting Spinner
        SettingUpSpinner(spinner);

        button = view.findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_due_date();
            }
        });

        genderTextView.setText(patientModel.getGender());


        genderTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            bottomSheetFragment.show(getChildFragmentManager(),bottomSheetFragment.getTag());
                genderTextView.setText(patientModel.getGender());
                Log.d("blood", "onClick: " + patientModel.getGender());
            }
        });


        savebutton.setOnClickListener(this);


        return view;
    }

    private void select_due_date(){


        DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                String str = dayOfMonth+"/"+(month+1)+"/"+year;

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                try {
                    Date date = dateFormat.parse(str);
                    long milliseconds = date.getTime();
                    patientModel.setDueDate(milliseconds);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Log.d("ee", "onDateSet: " + str);
            }
        }, 2022, 0, 15);
        dialog.show();


    }



    private void SettingUpSpinner(Spinner spinner) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.Blood_Group, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 spinnerText = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    @Override
    public void onClick(View v) {

        switch(v.getId()){

            case R.id.saveButton:
                SaveDataToFirebase();
                break;

        }

    }

    private void SaveDataToFirebase() {

        String firstName = firstNameEditText.getText().toString().trim();
        String lastName = lastNameEditText.getText().toString().trim();
        patientName = firstName + lastName;

        String gender = patientModel.getGender();
        String bloodGroup = spinnerText;
        String phoneNo = phoneNoEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String address = addressEditText.getText().toString().trim();
        String age = ageEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();

        if(!TextUtils.isEmpty(patientName) &&
                !TextUtils.isEmpty(gender) &&
                !TextUtils.isEmpty(email) &&
                !TextUtils.isEmpty(phoneNo) &&
                !TextUtils.isEmpty(bloodGroup) &&
                !TextUtils.isEmpty(age) &&
                !TextUtils.isEmpty(description) &&
                !TextUtils.isEmpty(address)
        ){

            try {
                addingDataToFirebase(patientName,
                        gender,
                        phoneNo,
                        bloodGroup,
                        age,
                        description,
                        address,
                        email,
                        patientModel.getDueDate());

            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

        }
        else{
            Toast.makeText(getContext(), "Fill The Form", Toast.LENGTH_SHORT).show();
        }
    }

    private void addingDataToFirebase(String patientName,
                                      String gender,
                                      String phoneNo,
                                      String bloodGroup,
                                      String age,
                                      String description,
                                      String address,
                                      String email,
                                      long dueDate) throws ParseException {

        UserModel userModel = UserModel.getInstance();

        patientModel.setPatientName(patientName);
        patientModel.setGender(gender);
        patientModel.setSeverity("Not that Severe");
        patientModel.setEmail(email);
        patientModel.setPhoneNo(phoneNo);
        patientModel.setBloodGroup(bloodGroup);
        patientModel.setAddress(address);
        patientModel.setDescription(description);
        patientModel.setAge(age);
        patientModel.setRelationToPatient("Friend");
        long due = System.currentTimeMillis() + DateUtils.DAY_IN_MILLIS;
        patientModel.setDueDate(dueDate);
        patientModel.setPostedOn(System.currentTimeMillis());
        patientModel.setUserName(userModel.getUserName());



        patientRequestCollection.add(patientModel).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {

                if(task.isSuccessful()){

                    task.getResult().addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                            if(error!=null){
                                Toast.makeText(getContext(), "Data is Null", Toast.LENGTH_SHORT).show();
                            }
                            if(value!=null && !value.exists()){
                                Toast.makeText(getContext(), "Request Successful", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }
                else{
                    Toast.makeText(getContext(), "Request Failed", Toast.LENGTH_SHORT).show();
                    Log.d("requestblood", "onComplete: Failure of Task: ");
                }

            }
        });



    }
}