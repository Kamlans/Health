package com.example.aihealth.Pathologist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.aihealth.Hospital.HospitalMainActivity;
import com.example.aihealth.Hospital.HospitalProfileActivity;
import com.example.aihealth.R;
import com.example.aihealth.Utils.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

public class PathologistProfileActivity extends AppCompatActivity {


    private FirebaseFirestore firestore ;
    private FirebaseStorage storage;
    private StorageReference storageRef ;
    private FirebaseAuth auth;
    private EditText name , phNum , location;
    private ImageView imageView;
    private Context context;
    private String imgUrl;
    private ImageButton imageButton;
    private Button continueBtn;
    private Uri filepath;
    private Bitmap bitmap;
    private ProgressBar progressBar;
    private TextView progressPercentage;

    private static final String TAG = "kamlans";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_pathologist_profile);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        name =  findViewById(R.id.nameBoxOfpathologist);
        phNum =  findViewById(R.id.phone_number_boxOfPathologist);
        location =  findViewById(R.id.location_boxOfPathologist);
        imageView =  findViewById(R.id.imageViewOfPathologist);
        imageButton =  findViewById(R.id.selectNewDpOFPathologist);
        continueBtn =  findViewById(R.id.continueBtnOfPathologist);
        progressBar =  findViewById(R.id.progressBarOfPathlogist);
        progressPercentage =  findViewById(R.id.progressPercentageOfPathologist);

        progressBar.setVisibility(View.GONE);

        firestore.collection(Constants.Pathologist).document(FirebaseAuth.getInstance().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            Glide.with(getApplicationContext() )
                                    .load(documentSnapshot.get("img"))
                                    .into(imageView);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: "+e);
                    }
                });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withContext(getApplicationContext()).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                                Intent intent = new Intent( Intent.ACTION_PICK);
                                intent.setType("image/*");
                                Log.d(TAG, "onPermissionGranted: clicked edit");
                                startActivityForResult(Intent.createChooser( intent , "please select image") , 1);

                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                                Log.d(TAG, "onPermissionDenied: ");
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                                Log.d(TAG, "onPermissionRationaleShouldBeShown: ");
                            }
                        }).check();
            }
        });

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                continueBtn.setClickable(false);
                if (TextUtils.isEmpty(name.getText())){
                    Toast.makeText(context, "Enter name", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(phNum.getText())){
                    Toast.makeText(context, "Enter phone number", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(location.getText())){
                    Toast.makeText(context, "Enter location", Toast.LENGTH_SHORT).show();
                }
                if (imageView == null){
                    Toast.makeText(context, "enter a valid image", Toast.LENGTH_SHORT).show();
                }

                else{
                    uploadToFirebase();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK){
            filepath = data.getData();


            try {
                InputStream inputStream = getContentResolver().openInputStream(filepath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
                Log.d(TAG, "onActivityResult: inside try block");

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Log.d(TAG, "onActivityResult: "+e);
            }


        }
    }

    protected void uploadToFirebase(){

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference().child("dp").child(auth.getUid()+"dp");



        storageReference.putFile(filepath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Toast.makeText(PathologistProfileActivity.this, "File Uploaded", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onSuccess: file uploaded");

                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {




                                try{
                                    String HospName = name.getText().toString();
                                    String HospPhNum = phNum.getText().toString();
                                    String HospLocation = location.getText().toString();

                                    HashMap<String , Object> data = new HashMap<>();
                                    data.put("name" , HospName);
                                    data.put("phNum" , HospPhNum);
                                    data.put("location" , HospLocation);
                                    data.put("img" , uri.toString());
                                    data.put( Constants.UserType , Constants.UserTypeIsPathologist);

                                    Log.d(TAG, "onSuccess: "+HospName +HospPhNum +HospLocation);
                                    firestore.collection("user").document(auth.getUid())
                                            .set(data)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d(TAG, "onSuccess: "+data.toString());

                                                    progressBar.setVisibility(View.GONE);
                                                    startActivity( new Intent(getApplicationContext() , PathologistMainActivity.class));
                                                    Log.d(TAG, "onSuccess: "+uri.toString());

                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.d(TAG, "onFailure: "+e);
                                                }
                                            });

                                    firestore.collection(Constants.Pathologist).document(auth.getUid())
                                            .set(data)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d(TAG, "onSuccess: "+data.toString());

                                                    progressBar.setVisibility(View.GONE);
                                                    startActivity( new Intent(getApplicationContext() , PathologistMainActivity.class));
                                                    Log.d(TAG, "onSuccess: "+uri.toString());

                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.d(TAG, "onFailure: "+e);
                                                }
                                            });
                                }
                                catch ( Exception e){
                                    Log.d(TAG, "trycatch: "+e);
                                }


                            }
                        });
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                        float percent = (100 * snapshot.getBytesTransferred())/snapshot.getTotalByteCount();

                        progressPercentage.setText( String.valueOf(percent) +"%");

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: Error in uploading");
                        Toast.makeText(getApplicationContext(), " Error in uploading", Toast.LENGTH_SHORT).show();
                    }
                });


    }
}