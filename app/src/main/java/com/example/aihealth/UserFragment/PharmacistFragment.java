package com.example.aihealth.UserFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aihealth.Adapters.HospitalAdapter;
import com.example.aihealth.Adapters.PharmacistAdapter;
import com.example.aihealth.Models.Hospital;
import com.example.aihealth.Models.Pharmacist;
import com.example.aihealth.R;
import com.example.aihealth.Utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class PharmacistFragment extends Fragment {

    List<Pharmacist> list = new ArrayList<>();
    private static final String TAG = "kamlans";


    public PharmacistFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pharmacist, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.pharmacistRecyclerView);
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        PharmacistAdapter pharmacistAdapter = new PharmacistAdapter(getContext() , list);

        firestore.collection(Constants.Pharmacist)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {


                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                                try {
                                    Pharmacist pharmacist = new Pharmacist(
                                            documentSnapshot.get(Constants.Name).toString() ,
                                            documentSnapshot.get(Constants.PhoneNumber).toString() ,
                                            documentSnapshot.get(Constants.Location).toString() ,
                                            documentSnapshot.get(Constants.Image).toString()
                                    );

                                    list.add(pharmacist);

//                                    Pharmacist pharmacist = new Pharmacist("name" , "phone" ,"location" , "https://firebasestorage.googleapis.com/v0/b/ai-health-cb1fe.appspot.com/o/dp%2FespOzLJujPMzS4ZcwSxiYjUGf8B2dp?alt=media&token=4693fd44-c8a2-4bb6-ae21-9e99c446f035");
//
//                                    list.add(pharmacist);
//                                    Log.d(TAG, "onComplete: "+pharmacist.toString());

                                    Log.d(TAG, "onComplete: "+documentSnapshot.getData().toString());
                                }
                                catch (Exception e){
                                    Log.d(TAG, "error: "+e);
                                }


                            }


                            pharmacistAdapter.notifyDataSetChanged();
                        }
                        else{
                            Log.d(TAG, "onComplete: there is an error");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: "+e);
                    }
                });

        pharmacistAdapter.notifyDataSetChanged();
        recyclerView.bringToFront();
        recyclerView.setAdapter(pharmacistAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        return view;
    }
}