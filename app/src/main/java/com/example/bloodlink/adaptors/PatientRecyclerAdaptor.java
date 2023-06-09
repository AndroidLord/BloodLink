package com.example.bloodlink.adaptors;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodlink.PatientActivity;
import com.example.bloodlink.R;
import com.example.bloodlink.model.PatientModel;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PatientRecyclerAdaptor extends RecyclerView.Adapter<PatientRecyclerAdaptor.ViewHolder> {

    private Context context;
    private List<PatientModel> patientModelList;


    public PatientRecyclerAdaptor() {

    }

    public PatientRecyclerAdaptor(Context context, List<PatientModel> patientModelList) {
        this.context = context;
        this.patientModelList = patientModelList;
    }

    @NonNull
    @Override
    public PatientRecyclerAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.patient_recycler_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientRecyclerAdaptor.ViewHolder holder, int position) {

        PatientModel patientModel = patientModelList.get(position);

        holder.titleTextView.setText("Need "+patientModel.getBloodGroup()+" Blood Donar");
        holder.locationTextView.setText(patientModel.getAddress());

        Calendar calendar = Calendar.getInstance();
        DateFormat obj = new SimpleDateFormat("d MMMM yyyy, hh:mma");
        // we create instance of the Date and pass milliseconds to the constructor
        Date res = new Date(patientModel.getPostedOn());
        holder.postedOnTextView.setText(obj.format(res));

        String DueDateStr = formatDate(new Date(patientModel.getDueDate()));
        holder.dueDateTextView.setText("Due Date: "+DueDateStr+" ");
        holder.authorTextView.setText(patientModel.getUserName());
        holder.descriptionTextView.setText("Description: "+patientModel.getDescription());

        Picasso.get()
                .load(patientModel.getUserImage())
                .placeholder(R.drawable.user)
                .into(holder.userImageView);

    }

    @Override
    public int getItemCount() {
        return patientModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView titleTextView,
                         authorTextView,
                         locationTextView,
                         postedOnTextView,
                         dueDateTextView,
                         descriptionTextView;

        private ImageView userImageView;
        private OnPatientDetailTransfer onPatientDetailTransfer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.patient_title_item);
            authorTextView = itemView.findViewById(R.id.userName_item);
            locationTextView = itemView.findViewById(R.id.location_item);
            postedOnTextView = itemView.findViewById(R.id.postedOn_item);
            dueDateTextView = itemView.findViewById(R.id.dueTime_item);
            descriptionTextView = itemView.findViewById(R.id.description_item);
            userImageView = itemView.findViewById(R.id.userImage_item);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PatientModel patientModel = patientModelList.get(getAdapterPosition());
                    Intent intent = new Intent(context,PatientActivity.class);
                    intent.putExtra("patient",patientModel);
                    context.startActivity(intent);
                }
            });

        }

    }
    public PatientModel getPatientDetails(int position){
        if(patientModelList!=null){
                if(patientModelList.size() > 0){
                    return patientModelList.get(position);
                }
        }
        return null;
    }


    public static String formatDate(Date dueDate) {
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
