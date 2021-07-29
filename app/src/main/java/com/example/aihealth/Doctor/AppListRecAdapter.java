package com.example.aihealth.Doctor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aihealth.Models.AppointmentModel;
import com.example.aihealth.R;

import java.util.List;

public class AppListRecAdapter extends RecyclerView.Adapter<AppListRecAdapter.ViewHolder> {

    Context context;
    List<AppointmentModel> list;

    public AppListRecAdapter(Context context, List<AppointmentModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.single_appointment_row_doc_activity , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.appointment.setText(list.get(position).getAppointment());
        holder.symptoms.setText(list.get(position).getSymptoms());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private   TextView appointment , symptoms;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            appointment = itemView.findViewById(R.id.appointmentOfWhichUser);
            symptoms = itemView.findViewById(R.id.appointmentSymptoms);
        }
    }
}
