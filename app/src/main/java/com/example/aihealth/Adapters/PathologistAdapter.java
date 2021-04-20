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
import com.example.aihealth.Models.Pathologist;
import com.example.aihealth.R;

import java.util.List;

public class PathologistAdapter extends RecyclerView.Adapter<PathologistAdapter.ViewHolder> {

    Context context;
    List<Pathologist> list;

    public PathologistAdapter(Context context, List<Pathologist> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PathologistAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.single_pathologist , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImgUrl()).into(holder.imageView);
        holder.name.setText(list.get(position).getName());
        holder.phNum.setText(list.get(position).getPhNum());
        holder.location.setText(list.get(position).getLocation());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView name, phNum, location;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageSinglePathologist);
            name = itemView.findViewById(R.id.nameOfPathologistInSinglePathologist);
            phNum = itemView.findViewById(R.id.phNUmOfPathologistInSinglePathologist);
            location = itemView.findViewById(R.id.locationOfPathologistInSinglePathologist);
        }
    }
}
