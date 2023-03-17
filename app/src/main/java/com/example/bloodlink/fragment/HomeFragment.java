package com.example.bloodlink.fragment;

import android.graphics.PathEffect;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bloodlink.R;
import com.example.bloodlink.adaptors.PatientRecyclerAdaptor;
import com.example.bloodlink.model.PatientModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private PatientRecyclerAdaptor patientRecyclerAdaptor;

    private List<PatientModel> patientModelList;

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

        PatientModel patientModel = new PatientModel();
        patientModel.setPatientName("Vineet");
        patientModel.setAddress("Sahara Hospital");
        patientModel.setPostedOn("Posted on: 05:45, 14/03/23");
        patientModel.setDueDate("Tomorrow");
        patientModel.setDescription("Description: My brother had an accident late night and is urgent need of blood. Please Help! \uD83D\uDE4F\uD83D\uDE4F");

        for (int i=0;i<10;i++){
            patientModelList.add(patientModel);
        }

        // Setting Recycler View
        patientRecyclerAdaptor = new PatientRecyclerAdaptor(getContext(),patientModelList);
        recyclerView.setAdapter(patientRecyclerAdaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



        return view;
    }
}