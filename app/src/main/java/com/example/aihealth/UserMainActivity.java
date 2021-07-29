package com.example.aihealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.aihealth.UserFragment.DoctorFragment;
import com.example.aihealth.UserFragment.HospitalFragment;
import com.example.aihealth.UserFragment.IntroFragment;
import com.example.aihealth.UserFragment.PathologistFragment;
import com.example.aihealth.UserFragment.PharmacistFragment;
import com.example.aihealth.Utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserMainActivity extends AppCompatActivity {
    private static final String TAG = "kamlans";

    private Toolbar toolbar ;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FloatingActionButton fab;
    private CircleImageView profileImageInNavHeader;
    private TextView nameInNavHeader , phoneInNavHeader;
    private Button editProfileBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.nav_view);

        View headerView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.nav_header , drawerLayout , false);
        navigationView.addHeaderView(headerView);


        profileImageInNavHeader = headerView.findViewById(R.id.drawerImageOfUser);
        nameInNavHeader = headerView.findViewById(R.id.nameInDrawerOfUser);
        phoneInNavHeader = headerView.findViewById(R.id.phNumInDrawerOfUser);
        editProfileBtn = headerView.findViewById(R.id.editBtnInDrawerOfUser);


        FirebaseFirestore.getInstance().collection(Constants.User).document(FirebaseAuth.getInstance().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot documentSnapshot = task.getResult();

                            if (documentSnapshot.exists()){

                                Log.d(TAG, "onComplete: "+documentSnapshot.getData().toString());

                                Glide.with(getApplicationContext())
                                        .load(documentSnapshot.get(Constants.Image).toString()).into(profileImageInNavHeader);
                                nameInNavHeader.setText(documentSnapshot.get(Constants.Name).toString());
                                phoneInNavHeader.setText(documentSnapshot.get(Constants.PhoneNumber).toString());

                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Log.d(TAG, "onFailure: "+e);

            }
        });







        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked edit button");
                startActivity(new Intent(getApplicationContext() , UserProfileActivity.class ));
            }
        });




        toolbar = findViewById(R.id.toolabar);
        setSupportActionBar(toolbar);



        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this ,
                drawerLayout ,
                toolbar ,
                R.string.navigation_drawer_open ,
                R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( getApplicationContext() , BookAppoitmentActivity.class));
            }
        });

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add( R.id.frame , new IntroFragment() )
                    .addToBackStack(null)
                    .commit();
        }


        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                switch (item.getItemId()){

                    case R.id.homeMenuItem:
                        item.setChecked(true);
                        toolbar.setTitle("HOME");
                        getSupportFragmentManager().beginTransaction()
                                .replace( R.id.frame , new IntroFragment() )
                                .addToBackStack(null)
                                .commit();
                        Toast.makeText(UserMainActivity.this, "Doctor clicked", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onNavigationItemSelected: doctor");
                        break;


                    case R.id.doctorMenuItem:
                        item.setChecked(true);
                        toolbar.setTitle("DOCTOR");
                        getSupportFragmentManager().beginTransaction()
                                .replace( R.id.frame , new DoctorFragment() )
                                .addToBackStack(null)
                                .commit();
                        Toast.makeText(UserMainActivity.this, "Doctor clicked", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onNavigationItemSelected: doctor");
                        break;

                    case R.id.pathologistMenuItem:
                        item.setChecked(true);
                        toolbar.setTitle("PATHOLOGIST");
                        getSupportFragmentManager().beginTransaction()
                                .replace( R.id.frame , new PathologistFragment() )
                                .addToBackStack(null)
                                .commit();
                        Log.d(TAG, "onNavigationItemSelected: patho");
                        Toast.makeText(UserMainActivity.this, "Patho clicked", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.pharmacistMenuItem:
                        toolbar.setTitle("PHARMACIST");
                        getSupportFragmentManager().beginTransaction()
                                .replace( R.id.frame , new PharmacistFragment() )
                                .addToBackStack(null)
                                .commit();
                        Log.d(TAG, "onNavigationItemSelected: pharma");
                        break;

                    case R.id.hospitalMenuItem:
                        toolbar.setTitle("HOPSPITAL");
                        getSupportFragmentManager().beginTransaction()
                                .replace( R.id.frame , new HospitalFragment() )
                                .addToBackStack(null)
                                .commit();
                        Log.d(TAG, "onNavigationItemSelected: hospital");
                        break;




                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.toolbar_menu , menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity( new Intent(getApplicationContext() , LoginActivity.class));

        }
        return true;
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) drawerLayout.closeDrawer(GravityCompat.START);
        else super.onBackPressed();
    }
}