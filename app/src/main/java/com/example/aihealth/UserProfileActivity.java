package com.example.aihealth;

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

public class UserProfileActivity extends AppCompatActivity {

    private FirebaseFirestore firestore ;
    private FirebaseStorage storage;
    private StorageReference storageRef ;
    private FirebaseAuth auth;
    private EditText name , phNum;
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
        setContentView(R.layout.activity_user_profile);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        name =  findViewById(R.id.nameBoxOfUser);
        phNum =  findViewById(R.id.phone_number_boxOfUser);
        imageView =  findViewById(R.id.imageViewofUser);
        imageButton =  findViewById(R.id.selectNewDpOFUser);
        continueBtn =  findViewById(R.id.continueBtnOfUser);
        progressBar =  findViewById(R.id.progressBarOfUser);
        progressPercentage =  findViewById(R.id.progressPercentage);


        progressBar.setVisibility(View.GONE);

        firestore.collection("user").document(FirebaseAuth.getInstance().getUid())
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


//
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity( new Intent(getApplicationContext() , DisplayImage.class));
//            }
//        });


        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                uploadToFirebase();
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

            } catch (FileNotFoundException e) {
                e.printStackTrace();
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

                        Toast.makeText(UserProfileActivity.this, "File Uploaded", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onSuccess: file uploaded");

                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                String userName = name.getText().toString();
                                String userPhNum = phNum.getText().toString();


                                HashMap<String , Object> data = new HashMap<>();
                                data.put("img" , uri.toString());
                                data.put("name" , userName);
                                data.put("phNum" , userPhNum);
                                data.put(Constants.UserType , Constants.UserTypeIsUser);

                                firestore.collection("user")
                                        .document(auth.getUid())
                                        .set(data)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {

                                                progressBar.setVisibility(View.GONE);

                                                Toast.makeText(UserProfileActivity.this, "url is "+uri.toString() , Toast.LENGTH_SHORT).show();
                                                startActivity( new Intent( getApplicationContext() , UserMainActivity.class));
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d(TAG, "onFailure: " +e);
                                            }
                                        });


//                                Toast.makeText(ProfileActivity.this, "url is"+uri, Toast.LENGTH_SHORT).show();
//                                startActivity( new Intent( getApplicationContext() , MainActivity.class));

                            }
                        });
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                        float percent = (100 * snapshot.getBytesTransferred())/snapshot.getTotalByteCount();

                        progressPercentage.setText( String.valueOf(percent));

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: Error in uploading");
                        Toast.makeText(UserProfileActivity.this, " Error in uploading", Toast.LENGTH_SHORT).show();
                    }
                });


    }
}