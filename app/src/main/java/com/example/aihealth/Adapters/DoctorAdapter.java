package com.example.aihealth.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.aihealth.Models.Doctor;
import com.example.aihealth.MoreInfoActivity;
import com.example.aihealth.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.ViewHolder> {

    Context context ;
    List<Doctor> list;

    public DoctorAdapter(Context context, List<Doctor> list) {
        this.context = context;
        this.list = list;
    }

    public DoctorAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.single_doctor , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.nameOfDoctor.setText(list.get(position).getNameOfDoctor());
        holder.qualificationOfDoctor.setText(list.get(position).getQualificationOfDoctor());
        holder.specializationOfDoctor.setText(list.get(position).getSpecializationOfDoctor());
        holder.phoneNumOfDoctor.setText(list.get(position).getPhoneNumOfDoctor());

       // Glide.with(context).load(list.get(position).getImageOfDoctorURI()).into(holder.imageOfDoctor);
        Picasso.get().load(list.get(position).getImageOfDoctorURI()).into(holder.imageOfDoctor);

        holder.linearLayout.bringToFront();

        holder.frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( context , MoreInfoActivity.class);
                intent.putExtra("name" ,list.get(position).getNameOfDoctor());
                intent.putExtra("qualification" ,list.get(position).getQualificationOfDoctor());
                intent.putExtra("specialization" ,list.get(position).getSpecializationOfDoctor());
                intent.putExtra("phNum" ,list.get(position).getPhoneNumOfDoctor());
                intent.putExtra("img" ,list.get(position).getImageOfDoctorURI());

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageOfDoctor;
        private TextView nameOfDoctor ,specializationOfDoctor  , phoneNumOfDoctor , qualificationOfDoctor;
        private LinearLayout linearLayout;
        FrameLayout frameLayout;
        public ViewHolder(@NonNull View itemView) {
           super(itemView);
            imageOfDoctor = itemView.findViewById(R.id.imageOfDoc);
            nameOfDoctor = itemView.findViewById(R.id.nameOfDoc);
            specializationOfDoctor = itemView.findViewById(R.id.specializationOfDoc);
            phoneNumOfDoctor = itemView.findViewById(R.id.phNumOfDoc);
            qualificationOfDoctor = itemView.findViewById(R.id.qualificationOfDoc);
            frameLayout = itemView.findViewById(R.id.doctorLayout);
            linearLayout = itemView.findViewById(R.id.singleDoctorLinearLayout);
        }
    }
}
