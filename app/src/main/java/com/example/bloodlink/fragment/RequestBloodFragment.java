package com.example.bloodlink.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bloodlink.R;
import com.example.bloodlink.model.PatientModel;
import com.example.bloodlink.model.UserModel;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class RequestBloodFragment extends Fragment  {


    private EditText patientName,
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
    private String spinnerText, dueDateStr;
    private Long dueDate,postedOn;
    private CalendarView calendarView;

    // Setting Up Firebase
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference patientRequestCollection = db.collection("PatientRequestCollection");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_request_blood, container, false);


        // ID's
        Spinner spinner = view.findViewById(R.id.spinner);

        //Setting Spinner
        SettingUpSpinner(spinner);

        // Setting Calendar
        SettingUpCalendar();

        try {
            addingDataToFirebase();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return view;
    }

    private void SettingUpCalendar() {
//        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//            @Override
//            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
//                dueDateStr = dayOfMonth+"/"+(month+1)+"/"+year;
//            }
//        });
    }

    private void addingDataToFirebase() throws ParseException {

        UserModel userModel = UserModel.getInstance();

        Map<String,String> patientRequestMap = new HashMap<>();
        patientRequestMap.put("patientName","Vivek");
        patientRequestMap.put("gender","Other");
        patientRequestMap.put("severity","Upto You");
        patientRequestMap.put("userId",userModel.getUserId());
        patientRequestMap.put("email",userModel.getEmailId());
        patientRequestMap.put("phoneNo","6393664992");
        patientRequestMap.put("bloodGroup","o+");
        patientRequestMap.put("address","Beyond Lulu Mall");
        patientRequestMap.put("description","I just need blood and this is a request form so.. please help me!!");
        patientRequestMap.put("age","20");
        patientRequestMap.put("relationToPatient","Self");
//
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MM yyyy");
//
//            Date date = simpleDateFormat.parse(dueDateStr);
//            if(date!=null){
//                dueDate = date.getTime();
//            }
//            else{
//                dueDate = DateUtils.DAY_IN_MILLIS + System.currentTimeMillis();
//                Toast.makeText(getContext(), "Error Setting Date, Making Due to Tomorrow", Toast.LENGTH_SHORT).show();
//            }
        patientRequestMap.put("dueDate", "Tomorrow");//dueDate

        // Code for Date, on the day it was posted
        postedOn = System.currentTimeMillis();
//        if((dueDate - postedOn) < 0){
//            Toast.makeText(getContext(), "Please Enter A Valid Due Date", Toast.LENGTH_SHORT).show();
//            return;
//        }
        patientRequestMap.put("postedOn", String.valueOf(postedOn));//postedOn

        patientRequestMap.put("userName",userModel.getUserName());//userName


        patientRequestCollection.add(patientRequestMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
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


}