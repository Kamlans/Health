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
import com.example.aihealth.Adapters.PathologistAdapter;
import com.example.aihealth.Models.Hospital;
import com.example.aihealth.Models.Pathologist;
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


public class PathologistFragment extends Fragment {

    List<Pathologist> list = new ArrayList<Pathologist>();
    private static final String TAG = "kamlans";



    public PathologistFragment() {
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
        View view = inflater.inflate(R.layout.fragment_pathologist, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.pathologistRecyclerView);
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        PathologistAdapter pathologistAdapter = new PathologistAdapter(getContext() , list);

        firestore.collection(Constants.Pathologist)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {


                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                                try {
                                    Pathologist pathologist = new Pathologist(
                                            documentSnapshot.get(Constants.Name).toString() ,
                                            documentSnapshot.get(Constants.PhoneNumber).toString() ,
                                            documentSnapshot.get(Constants.Location).toString() ,
                                            documentSnapshot.get(Constants.Image).toString()
                                    );

                                    list.add(pathologist);

                                    Log.d(TAG, "onComplete: "+pathologist.toString());

                                    Log.d(TAG, "onComplete: "+documentSnapshot.getData().toString());
                                }
                                catch (Exception e){
                                    Log.d(TAG, "error: "+e);
                                }


                            }


                            pathologistAdapter.notifyDataSetChanged();
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

        pathologistAdapter.notifyDataSetChanged();
        recyclerView.bringToFront();
        recyclerView.setAdapter(pathologistAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
}