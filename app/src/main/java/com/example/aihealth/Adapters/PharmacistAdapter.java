package com.example.aihealth.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.aihealth.Models.Pharmacist;
import com.example.aihealth.R;

import java.util.List;

public class PharmacistAdapter extends RecyclerView.Adapter<PharmacistAdapter.ViewHolder>{

    Context context;
    List<Pharmacist> list;

    public PharmacistAdapter(Context context, List<Pharmacist> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PharmacistAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.single_phamacist , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).load(list.get(position).getImgUrl()).into(holder.imageView);
        holder.name.setText(list.get(position).getName());
        holder.phNum.setText(list.get(position).getPhNum());
        holder.location.setText(list.get(position).getLocatin());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView name , phNum , location;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageSinglePharmacist);
            name = itemView.findViewById(R.id.nameOfPharmacistInSinglePharmacist);
            phNum = itemView.findViewById(R.id.phNUmOfPharmacistInSinglePharmacist);
            location = itemView.findViewById(R.id.locationOfPharmacistInSinglePharmacist);

        }
    }
}
