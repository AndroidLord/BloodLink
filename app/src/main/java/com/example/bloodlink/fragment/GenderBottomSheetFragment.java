package com.example.bloodlink.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.bloodlink.R;
import com.example.bloodlink.model.PatientModel;
import com.example.bloodlink.model.UserModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class GenderBottomSheetFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    private Button male,female,preferNotToSay;
    private PatientModel patientModel = PatientModel.getInstance();
    public GenderBottomSheetFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_gender_bottom_sheet, container, false);


        male = view.findViewById(R.id.maleGender_BottomSheetFragment);
        female = view.findViewById(R.id.femaleGender_BottomSheetFragment);
        preferNotToSay = view.findViewById(R.id.preferNotToSayGender_BottomSheetFragment);

        male.setOnClickListener(this);
        female.setOnClickListener(this);
        preferNotToSay.setOnClickListener(this);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }



    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.maleGender_BottomSheetFragment:
                    patientModel.setGender("Male");
                break;
            case R.id.femaleGender_BottomSheetFragment:
                    patientModel.setGender("Female");
                break;
            case R.id.preferNotToSayGender_BottomSheetFragment:
                    patientModel.setGender("Prefer Not To Say");
                break;
        }

    }
}