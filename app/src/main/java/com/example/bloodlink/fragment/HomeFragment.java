package com.example.bloodlink.fragment;

import android.content.Intent;
import android.graphics.PathEffect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bloodlink.PatientActivity;
import com.example.bloodlink.R;
import com.example.bloodlink.adaptors.OnPatientDetailTransfer;
import com.example.bloodlink.adaptors.OnPatientRequestListener;
import com.example.bloodlink.adaptors.PatientRecyclerAdaptor;
import com.example.bloodlink.model.PatientModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private PatientRecyclerAdaptor patientRecyclerAdaptor;

    private List<PatientModel> patientModelList;
    OnPatientDetailTransfer onPatientDetailTransfer;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("PatientRequestCollection");

    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_home, container, false);
        // ID's
        recyclerView = view.findViewById(R.id.recyclerView_main);

         // Initiating List
        patientModelList = new ArrayList<>();


       return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.d("home", "onStart: Going to Retrieve Data");


        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Kolkata"));
        calendar.setTimeInMillis(System.currentTimeMillis());

        Log.d("test", "onStart: millisecond "+ calendar.getTimeInMillis());

        collectionReference
                .whereGreaterThanOrEqualTo("dueDate",calendar.getTimeInMillis())
                .orderBy("dueDate")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if(error!=null){
                            Toast.makeText(getContext(), "Error Getting List", Toast.LENGTH_SHORT).show();
                            Log.d("home", "onEvent: error: "+error);
                        }
                        if(value!=null && !value.isEmpty()){
                            patientModelList.clear();
                            for(QueryDocumentSnapshot documentSnapshot: value){
                                PatientModel patientModel = documentSnapshot.toObject(PatientModel.class);
                                patientModelList.add(patientModel);
                                Log.d("home", "onEvent: List of Request: " + patientModel.getDueDate());
                            }

                            patientRecyclerAdaptor = new PatientRecyclerAdaptor(getContext(),patientModelList);
                            recyclerView.setAdapter(patientRecyclerAdaptor);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            patientRecyclerAdaptor.notifyDataSetChanged();
                        }
                        else{
                            Snackbar.make(recyclerView,"No Blood Reqeust Found", Snackbar.LENGTH_SHORT).show();
                        }

                    }
                });

    }

}