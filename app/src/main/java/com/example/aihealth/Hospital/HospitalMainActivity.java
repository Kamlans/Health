package com.example.aihealth.Hospital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.aihealth.LoginActivity;
import com.example.aihealth.R;
import com.google.firebase.auth.FirebaseAuth;

public class HospitalMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_main);

        findViewById(R.id.hospitalActivityLogoutBTn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity( new Intent(getApplicationContext() , LoginActivity.class));
                finish();
            }
        });
    }
}