package com.example.aihealth.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.aihealth.Models.Hospital;
import com.example.aihealth.R;

import java.util.List;

public class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.ViewHolder> {

    List<Hospital> list;
    Context context;
    private static final String TAG = "kamlans";

    public HospitalAdapter( Context context , List<Hospital> list ) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.single_hospital , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

      try{
          Glide.with(context).load(list.get(position).getImgUrl()).into(holder.imageView);
          //  holder.imageView.setImageResource(R.drawable.ic_launcher_background);
      }
      catch (Exception e){
          Log.d(TAG, "onBindViewHolder: glide "+e);
      }

        try{

            holder.name.setText(list.get(position).getName());

        }
        catch (Exception e){
            Log.d(TAG, "onBindViewHolder: name "+e);
        }

        try{

            holder.phNum.setText(list.get(position).getPhNum());

        }
        catch (Exception e){
            Log.d(TAG, "onBindViewHolder: phNum "+e);
        }

        try{

            holder.location.setText(list.get(position).getLocation());
        }
        catch (Exception e){
            Log.d(TAG, "onBindViewHolder: location"+e);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView name , phNum , location;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

           try{
               imageView = itemView.findViewById(R.id.imageSingleHospital);
               name = itemView.findViewById(R.id.nameOfHospitalInSingleHospital);
               phNum = itemView.findViewById(R.id.phNUmOfHospitalInSingleHospital);
               location = itemView.findViewById(R.id.locationOffHospitalInSingleHospital);
           }
           catch (Exception e){
               Log.d(TAG, "ViewHolder: "+e);
           }
        }
    }
}
