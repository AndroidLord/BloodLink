package com.example.bloodlink;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bloodlink.adaptors.OnPatientDetailTransfer;
import com.example.bloodlink.adaptors.PatientRecyclerAdaptor;
import com.example.bloodlink.model.PatientModel;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PatientActivity extends AppCompatActivity implements OnPatientDetailTransfer {

    PatientModel patientModel;

    private ImageView userImage,patientImage;
    
    
    private TextView userName,
            dueDate,
            postedOn,
            severity,
            title,

            phoneNo,
            email,
            relationToPatient,

    patientName,
    patientAge,
    patientGender,
    patientBloodGroup,
    patientDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

        // ID's
        title = findViewById(R.id.titleTextView_Patient);
        userName = findViewById(R.id.usernameTextView_Patient);
        //userImage = findViewById(R.id.userImage_Patient);
        dueDate = findViewById(R.id.dueDateTextView_Patient);
        severity = findViewById(R.id.severity_Patient);
       // patientImage = findViewById(R.id.patientImage_Patient);
        patientName = findViewById(R.id.patientNameTextView_Patient);
        patientAge = findViewById(R.id.ageTextView_Patient);
        patientGender = findViewById(R.id.genderTextView_Patient);
        patientBloodGroup = findViewById(R.id.bloodgroupTextView_Patient);
        patientDescription = findViewById(R.id.descriptionTextView_Patient);
        //phoneNo = findViewById(R.id.usernameTextView_Patient);
        //email = findViewById(R.id.usernameTextView_Patient);
        //relationToPatient = findViewById(R.id.usernameTextView_Patient);

        if(getIntent().hasExtra("patient")){
        patientModel = getIntent().getParcelableExtra("patient");

            title.setText("Urgent Need Of Blood "+patientModel.getBloodGroup());
            userName.setText(patientModel.getUserName());
            String DueDateStr = formatDate(new Date(patientModel.getDueDate()));
            dueDate.setText("Due Date: "+DueDateStr);
            severity.setText(patientModel.getSeverity());
            patientName.setText("Patient Name: "+patientModel.getPatientName());
            patientAge.setText("Patient Age: "+patientModel.getAge());
            patientGender.setText("Patient Gender: "+patientModel.getGender());
            patientBloodGroup.setText("Patient Blood Group: "+patientModel.getBloodGroup());
            patientDescription.setText("Description: "+patientModel.getDescription());


        }




    }

    @Override
    public void onPatientDetailTransfer(PatientModel patientModel) {
        this.patientModel = patientModel;
    }

    public String formatDate(Date dueDate) {
        long dueTime = dueDate.getTime();
        long now = System.currentTimeMillis();
        long timeDifference = dueTime - now;

        // Check if due date is in the future
        if (timeDifference > 0) {

            // Check if due date is tomorrow
            if (timeDifference < DateUtils.DAY_IN_MILLIS) {
                return "Tomorrow";
            }
            // Check if due date is within the next 7 days
            if (timeDifference < (DateUtils.WEEK_IN_MILLIS - DateUtils.DAY_IN_MILLIS)) {
                return DateUtils.getRelativeTimeSpanString(dueTime, now, DateUtils.DAY_IN_MILLIS, DateUtils.FORMAT_SHOW_WEEKDAY).toString();
            }
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, dd MMMM YYYY");
        String date = simpleDateFormat.format(dueDate);

        return date;
    }
}