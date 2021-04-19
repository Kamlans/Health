package com.example.aihealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.aihealth.Doctor.DoctorMainActivity;
import com.example.aihealth.Hospital.HospitalMainActivity;
import com.example.aihealth.Pathologist.PathologistMainActivity;
import com.example.aihealth.Pharmacist.PharmacistMainActivity;
import com.example.aihealth.Utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SplashScreenActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 2000;
    private static final String TAG = "kamlans";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler(Looper.getMainLooper()).postDelayed(
                new Runnable() {
                    @Override
                    public void run() {

                       // startActivity( new Intent(getApplicationContext() , LoginActivity.class));

                        if (FirebaseAuth.getInstance().getCurrentUser() != null){
                            FirebaseFirestore.getInstance().collection("user")
                                    .document(FirebaseAuth.getInstance().getUid())
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()){
                                                DocumentSnapshot documentSnapshot = task.getResult();
                                                if (documentSnapshot.exists()){
                                                    String x =  Constants.UserType;
                                                    String userType = documentSnapshot.get(x).toString();
                                                    Log.d(TAG, "onComplete: "+userType);

                                                    switch (userType){
                                                        case Constants.UserTypeIsUser:
                                                            try {
                                                                Log.d(TAG, "onComplete: user");
                                                                startActivity(new Intent(getApplicationContext(), UserMainActivity.class));
                                                            }
                                                            catch (Exception e){
                                                                Log.d(TAG, "onComplete: error "+e);
                                                            }
                                                            break;
                                                        case Constants.UserTypeIsDoctor:
                                                            Log.d(TAG, "onComplete: doctor");
                                                            startActivity(new Intent( getApplicationContext() , DoctorMainActivity.class));
                                                            break;
                                                        case Constants.UserTypeIsHospital:
                                                            Log.d(TAG, "onComplete: Hospital");
                                                            startActivity(new Intent( getApplicationContext() , HospitalMainActivity.class));
                                                            break;
                                                        case Constants.UserTypeIsPathologist:
                                                            Log.d(TAG, "onComplete: Pathologist");
                                                            startActivity(new Intent( getApplicationContext() , PathologistMainActivity.class));
                                                            break;
                                                        case Constants.UserTypeIsPharmacist:
                                                            Log.d(TAG, "onComplete: Pharmacist");
                                                            startActivity(new Intent( getApplicationContext() , PharmacistMainActivity.class));
                                                            break;
                                                    }
                                                }
                                            }
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d(TAG, "onFailure: "+e);
                                        }
                                    });
                        }
                        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                            startActivity( new Intent(getApplicationContext() , LoginActivity.class));

                        }


                    }

                }
       , SPLASH_DISPLAY_LENGTH );
    }
}