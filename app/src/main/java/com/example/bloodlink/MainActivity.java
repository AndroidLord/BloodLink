package com.example.bloodlink;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bloodlink.adaptors.PatientRecyclerAdaptor;
import com.example.bloodlink.model.PatientModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private PatientRecyclerAdaptor patientRecyclerAdaptor;

    private List<PatientModel> patientModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);







        // ID's
        recyclerView = findViewById(R.id.recyclerView_main);

        // Initiating List
        patientModelList = new ArrayList<>();

        PatientModel patientModel = new PatientModel("Need O+ Blood Donor",
                "Vineet",
                "Pheniox Plassio",
                "Posted on: 05:45, 14/03/23",
                "Tommorow",
                "Description: My brother had an accident late night and is urgent need of blood. Please Help! \uD83D\uDE4F\uD83D\uDE4F");

        for (int i=0;i<10;i++){
            patientModelList.add(patientModel);
        }

        // Setting Recycler View
        patientRecyclerAdaptor = new PatientRecyclerAdaptor(MainActivity.this,patientModelList);
        recyclerView.setAdapter(patientRecyclerAdaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }
}