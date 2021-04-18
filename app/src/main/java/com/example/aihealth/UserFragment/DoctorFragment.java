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

import com.example.aihealth.Adapters.DoctorAdapter;
import com.example.aihealth.Models.Doctor;
import com.example.aihealth.R;
import com.example.aihealth.Utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;


public class DoctorFragment extends Fragment {

    private static final String TAG = "kamlans";

    List<Doctor> list = new ArrayList<>();

    public DoctorFragment() {
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
        View view = inflater.inflate(R.layout.fragment_doctor, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.docFragmentRecView);
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        DoctorAdapter doctorAdapter = new DoctorAdapter(getContext(), list);

        firestore.collection("doctor")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                list.add(
                                        new Doctor(
                                                documentSnapshot.get(Constants.NameOfDoc).toString(),
                                                documentSnapshot.get(Constants.SpecializationOfDoc).toString(),
                                                documentSnapshot.get(Constants.PhoneNumberOfDoc).toString(),
                                                documentSnapshot.get(Constants.QualificationOfDoc).toString(),
                                                documentSnapshot.get(Constants.ImageOfDoctorURI).toString()
                                        ));

                                Log.d(TAG, "onComplete: "+ documentSnapshot.getId().toString());
                            }

                            doctorAdapter.notifyDataSetChanged();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: "+e);
                    }
                });
        doctorAdapter.notifyDataSetChanged();
        recyclerView.bringToFront();
        recyclerView.setAdapter(doctorAdapter);
        recyclerView.setLayoutManager( new LinearLayoutManager(getContext()));


        return view;
    }


}