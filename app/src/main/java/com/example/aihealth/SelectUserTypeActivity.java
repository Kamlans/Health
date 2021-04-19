package com.example.aihealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.example.aihealth.Doctor.DoctorMainActivity;
import com.example.aihealth.Doctor.DoctorProfileActivity;
import com.example.aihealth.Hospital.HospitalMainActivity;
import com.example.aihealth.Hospital.HospitalProfileActivity;
import com.example.aihealth.Pathologist.PathologistMainActivity;
import com.example.aihealth.Pathologist.PathologistProfileActivity;
import com.example.aihealth.Pharmacist.PharmacistMainActivity;
import com.example.aihealth.Pharmacist.PharmacistProfileActivity;

public class SelectUserTypeActivity extends AppCompatActivity {

    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user_type);


        radioGroup = findViewById(R.id.RadioGroup);
        radioGroup.clearCheck();

        findViewById(R.id.continueBtnOfSelectUserType).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent;

                int radioId = radioGroup.getCheckedRadioButtonId();

                switch(radioId){

                    case R.id.UserRadioButton:
                        intent = new Intent(getApplicationContext() , UserProfileActivity.class);

                        break;

                    case R.id.DoctorRadioButton:
                        intent = new Intent(getApplicationContext(), DoctorProfileActivity.class);

                        break;

                    case R.id.HospitalRadioButton:
                        intent = new Intent(getApplicationContext(), HospitalProfileActivity.class);

                        break;

                    case R.id.PharmacistRadioButton:
                        intent = new Intent(getApplicationContext() , PharmacistProfileActivity.class);

                        break;

                    case R.id.PathologistRadioButton:
                        intent = new Intent(getApplicationContext() , PathologistProfileActivity.class);

                        break;

                    default:
                        throw new IllegalStateException("Unexpected value: " + radioId);
                }

                startActivity(intent);

            }
        });
    }
}