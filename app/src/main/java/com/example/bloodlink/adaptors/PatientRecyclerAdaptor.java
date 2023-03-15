package com.example.bloodlink.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodlink.R;
import com.example.bloodlink.model.PatientModel;

import java.util.List;

public class PatientRecyclerAdaptor extends RecyclerView.Adapter<PatientRecyclerAdaptor.ViewHolder> {

    private Context context;
    private List<PatientModel> patientModelList;

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

        holder.locationTextView.setText(patientModel.getLocation());
        holder.postedOnTextView.setText(patientModel.getPostedOn());
        holder.dueDateTextView.setText(patientModel.getDueDate());
        holder.authorTextView.setText(patientModel.getAuthor());
        holder.descriptionTextView.setText(patientModel.getDescription());

    }

    @Override
    public int getItemCount() {
        return patientModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView titleTextView,
                         authorTextView,
                         locationTextView,
                         postedOnTextView,
                         dueDateTextView,
                         descriptionTextView;

        private ImageView userImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.patient_title_item);
            authorTextView = itemView.findViewById(R.id.author_item);
            locationTextView = itemView.findViewById(R.id.location_item);
            postedOnTextView = itemView.findViewById(R.id.postedOn_item);
            dueDateTextView = itemView.findViewById(R.id.dueTime_item);
            descriptionTextView = itemView.findViewById(R.id.description_item);
            userImageView = itemView.findViewById(R.id.imageView_item);

        }
    }
}
