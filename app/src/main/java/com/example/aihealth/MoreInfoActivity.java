package com.example.aihealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.aihealth.Utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class MoreInfoActivity extends AppCompatActivity implements PaymentResultListener {

    private static final String TAG = "kamlans";
    private TextView name, phNum, specs, quali, location , enteredSymptoms;
    private ImageView imageView;
    private Button paymentBtn , confirmSymptoms;
    Checkout checkout = new Checkout();
    String appointmentCollectionName , x , phone;
    String currentTime , successMsg;
    private EditText symptoms;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info);
        Checkout.preload(getApplicationContext());
        checkout.setKeyID(Constants.RazorPayKeyId);

        name = findViewById(R.id.nameInMoreInfo);
        phNum = findViewById(R.id.phnumInMoreInfo);
        specs = findViewById(R.id.specInMoreInfo);
        quali = findViewById(R.id.qualiInMoreInfo);
        location = findViewById(R.id.locationInMoreInfo);
        imageView = findViewById(R.id.imgInMoreInfo);
        paymentBtn = findViewById(R.id.paymentBtn);
        enteredSymptoms = findViewById(R.id.enteredSymptom);
        confirmSymptoms = findViewById(R.id.confirmSymptoms);
        symptoms = findViewById(R.id.symptomsInMoreInfo);

        Intent intent = getIntent();

        name.setText(intent.getStringExtra("name"));
        phNum.setText(intent.getStringExtra("phNum"));
        specs.setText(intent.getStringExtra("specialization"));
        quali.setText(intent.getStringExtra("qualification"));
        Glide.with(getApplicationContext()).load(intent.getStringExtra("img")).into(imageView);

        currentTime = Calendar.getInstance().getTime().toString();
        appointmentCollectionName = FirebaseAuth.getInstance().getUid()+currentTime+intent.getStringExtra("name")+intent.getStringExtra("phNum");

x =intent.getStringExtra("name");
phone = intent.getStringExtra("phNum");

        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPayment();
            }
        });
    }

    public void startPayment() {


        checkout.setImage(R.drawable.ic_baseline_edit_24);

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            options.put("name", x);
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            // options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", "50000");//pass amount in currency subunits
            options.put("name", x);
            options.put("prefill.contact", phone);
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch (Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        Log.d(TAG, "onPaymentSuccess: "+s);
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        successMsg = s;


        dataToFirebase();



    }

    @Override
    public void onPaymentError(int i, String s) {
        Log.d(TAG, "onPaymentError: "+s);
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();

    }

    private  void dataToFirebase(){
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        String value = symptoms.getText().toString().trim();

        HashMap<String  , Object> appointment = new HashMap<>();
        appointment.put("nameOfDoc" , x);
        appointment.put("uidOfPatient" , FirebaseAuth.getInstance().getUid());
        appointment.put("timeStamp" , currentTime);
        appointment.put("successMsg" , successMsg);
        appointment.put("symptom" , value);


        appointmentCollectionName = x+phone;


        if (TextUtils.isEmpty(value)){
            value = "No symptom entered";
        }

        firestore.collection(Constants.Appointment)
                .document(appointmentCollectionName)
                .set(appointment)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "onComplete: data upload completed");

                    }
                })
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: data successfully added");
                        Toast.makeText(getApplicationContext(), "Booked an appointment", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: "+e);
            }
        });
    }
}