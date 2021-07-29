package com.example.aihealth.Doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import com.example.aihealth.LoginActivity;
import com.example.aihealth.R;
import com.google.firebase.auth.FirebaseAuth;

public class DoctorMainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.doctorToolbar);
        setSupportActionBar(toolbar);


        findViewById(R.id.logoutDoc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity (new Intent(getApplicationContext() , LoginActivity.class));
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.doctor_toolbar_menu , menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.doclogout:
                FirebaseAuth.getInstance().signOut();
                startActivity( new Intent(getApplicationContext() , LoginActivity.class));

        }
        return true;
    }
}