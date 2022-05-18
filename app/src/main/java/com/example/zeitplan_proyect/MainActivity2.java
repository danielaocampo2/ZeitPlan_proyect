package com.example.zeitplan_proyect;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.example.zeitplan_proyect.DataBase.Firebase;
import com.example.zeitplan_proyect.Fragments.FragmentHelper;
import com.example.zeitplan_proyect.R;
import com.example.zeitplan_proyect.model.User;
import com.example.zeitplan_proyect.presenter.PresenterMenu;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity2  extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private User usuario;
    private TextView userName;
    private CircleImageView imgvw;
    private DrawerLayout drawer;
    PresenterMenu presenterMenu;
    Firebase db=Firebase.getInstance();


    //Variable para cambiar de fragments
    FragmentHelper fragmentHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fragmentHelper = new FragmentHelper(this);



        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupNavigationDrawerContent(navigationView);
        }

        setupNavigationDrawerContent(navigationView);
        fragmentHelper.setFragment(0);
        presenterMenu=new PresenterMenu(this);

        //inflate header layout
        View navView = navigationView.inflateHeaderView(R.layout.nav_header);

        //reference to views


        imgvw = (CircleImageView) navView.findViewById(R.id.imageView);

        userName = (TextView) navView.findViewById(R.id.textView1);
        //set views
        //  imgvw.setImageResource(R.drawable.your_image);

        //Iniciar firebase Auth
        presenterMenu.asignarImgNom(userName, imgvw);
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_2, menu);
        return true;
    }


    public void setupNavigationDrawerContent(@NonNull NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.nav_calendar:
                                menuItem.setChecked(true);
                                fragmentHelper.setFragment(0);
                                drawer.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.nav_add_activity:
                                menuItem.setChecked(true);
                                fragmentHelper.setFragment(1);
                                drawer.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.nav_add_subj:
                                menuItem.setChecked(true);
                                fragmentHelper.setFragment(2);
                                drawer.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.nav_notes:
                                menuItem.setChecked(true);
                                fragmentHelper.setFragment(3);
                                drawer.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.nav_calculadora:
                                menuItem.setChecked(true);
                                fragmentHelper.setFragment(5);
                                drawer.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.nav_out:
                                menuItem.setChecked(true);
                                presenterMenu.cierraSession();
                                fragmentHelper.setFragment(4);
                                drawer.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.nav_share:
                                menuItem.setChecked(true);
                                drawer.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.nav_help:
                                menuItem.setChecked(true);
                                usuario=User.getInstance();

                                Log.i(TAG, "NAME:  "+ usuario.name);
                                Log.i(TAG, "EMAIL22: "+ usuario.email);
                                Log.i(TAG, "ID22: "+ usuario.id);

                                drawer.closeDrawer(GravityCompat.START);
                                return true;
                        }
                        return true;
                    }
                });
    }
    public NavigationView getNavigationView(){
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        return navView;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
