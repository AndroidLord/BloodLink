package com.example.bloodlink;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.bloodlink.fragment.AboutUsFragment;
import com.example.bloodlink.fragment.AchievementFragment;
import com.example.bloodlink.fragment.HomeFragment;
import com.example.bloodlink.fragment.ProfileFragment;
import com.example.bloodlink.fragment.RequestBloodFragment;
import com.example.bloodlink.fragment.TermConditionFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class DrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    private FrameLayout fragmentContainer;

    // Setting up Firebase
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("PatientCollection");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        fragmentContainer = findViewById(R.id.fragment_container);

        setSupportActionBar(toolbar);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        //Initiating Firestore
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(currentUser!=null){

                }
                else{

                }
            }
        };

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        loadFragment(new HomeFragment());
        setTitle("Home");


    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.nav_profile:
                loadFragment(new ProfileFragment());
                //fragmentContainer.
                break;

            case R.id.nav_achievement:
                loadFragment(new AchievementFragment());
                break;

            case R.id.nav_bloodRequest:
                loadFragment(new RequestBloodFragment());
                break;

            case R.id.nav_termCondition:
                loadFragment(new TermConditionFragment());
                break;

            case R.id.nav_aboutUs:
                loadFragment(new AboutUsFragment());
                break;

            case R.id.nav_logout:
                logoutPrompt();
                break;
            default:
                loadFragment(new HomeFragment());
                break;

        }
        item.setChecked(true);
        if(item.getItemId()!=R.id.nav_logout)
        setTitle(item.getTitle());

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    private void logoutPrompt() {

        new AlertDialog.Builder(this)
                        .setTitle("Logout")
                        .setMessage("Do You Want To Logout")
                        .setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Toast.makeText(DrawerActivity.this, "You Have Logged out!", Toast.LENGTH_SHORT).show();
                                firebaseAuth.signOut();
                                startActivity(new Intent(DrawerActivity.this,LoginActivity.class));

                            }
                        })
                        .setNeutralButton("Return", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(DrawerActivity.this, "Wise Decision", Toast.LENGTH_SHORT).show();
                                loadFragment(new HomeFragment());
                            }
                        }).show();




    }

    private void loadFragment(Fragment fragment) {


        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();


        transaction.replace(R.id.fragment_container, fragment);
        //transaction.add(R.id.fragment_container,fragment);
        transaction.commit();


    }
}