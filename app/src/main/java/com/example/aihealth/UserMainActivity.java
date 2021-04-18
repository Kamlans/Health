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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.aihealth.UserFragment.DoctorFragment;
import com.example.aihealth.UserFragment.HospitalFragment;
import com.example.aihealth.UserFragment.IntroFragment;
import com.example.aihealth.UserFragment.PathologistFragment;
import com.example.aihealth.UserFragment.PharmacistFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class UserMainActivity extends AppCompatActivity {
    private static final String TAG = "UserMainActivity";

    private Toolbar toolbar ;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        toolbar = findViewById(R.id.toolabar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer);

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

        navigationView = findViewById(R.id.nav_view);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                switch (item.getItemId()){

                    case R.id.homeMenuItem:
                        item.setChecked(true);
                        getSupportFragmentManager().beginTransaction()
                                .replace( R.id.frame , new IntroFragment() )
                                .addToBackStack(null)
                                .commit();
                        Toast.makeText(UserMainActivity.this, "Doctor clicked", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onNavigationItemSelected: doctor");
                        break;


                    case R.id.doctorMenuItem:
                        item.setChecked(true);
                        getSupportFragmentManager().beginTransaction()
                                .replace( R.id.frame , new DoctorFragment() )
                                .addToBackStack(null)
                                .commit();
                        Toast.makeText(UserMainActivity.this, "Doctor clicked", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onNavigationItemSelected: doctor");
                        break;

                    case R.id.pathologistMenuItem:
                        item.setChecked(true);
                        getSupportFragmentManager().beginTransaction()
                                .replace( R.id.frame , new PathologistFragment() )
                                .addToBackStack(null)
                                .commit();
                        Log.d(TAG, "onNavigationItemSelected: patho");
                        Toast.makeText(UserMainActivity.this, "Patho clicked", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.pharmacistMenuItem:
                        getSupportFragmentManager().beginTransaction()
                                .replace( R.id.frame , new PharmacistFragment() )
                                .addToBackStack(null)
                                .commit();
                        Log.d(TAG, "onNavigationItemSelected: pharma");
                        break;

                    case R.id.hospitalMenuItem:
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

        }
        return true;
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) drawerLayout.closeDrawer(GravityCompat.START);
        else super.onBackPressed();
    }
}