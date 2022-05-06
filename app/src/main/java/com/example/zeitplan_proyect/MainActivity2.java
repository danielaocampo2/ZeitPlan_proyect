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
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.example.zeitplan_proyect.vista.Activity_login;
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

import androidx.fragment.app.FragmentTransaction;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity2  extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //Variable para gestionar FirebaseAuth
    private FirebaseAuth mAuth;

    //Variables opcionales para cerrar sesión en  de google
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInOptions gso;
    //private static final String TAG = "MainActivity2";

    private FirebaseFirestore mFirestore;
    private TextView userName;
    private CircleImageView imgvw;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mFirestore = FirebaseFirestore.getInstance();
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
        setFragment(0);


        //inflate header layout
        View navView = navigationView.inflateHeaderView(R.layout.nav_header);

        //reference to views


        imgvw = (CircleImageView) navView.findViewById(R.id.imageView);

        userName = (TextView) navView.findViewById(R.id.textView1);
        //set views
        //  imgvw.setImageResource(R.drawable.your_image);

        //Iniciar firebase Auth
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        verifyPro();

        navigationView.setNavigationItemSelectedListener(this);

        //Configurar las gso para google signIn con el fin de luego desloguear de google
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

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

    public void verifyPro() {
        String providerID = mAuth.getCurrentUser().getProviderData().get(1).getProviderId();
        String id = mAuth.getCurrentUser().getUid();
        switch (providerID) {
            case "google.com":
                userName.setText(mAuth.getCurrentUser().getDisplayName());
                Glide.with(this).load(mAuth.getCurrentUser().getPhotoUrl()).into(imgvw);
                break;
            case "password":
                mFirestore.collection("User").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            userName.setText(documentSnapshot.getString("name"));
                        }
                    }
                });
                Log.i(TAG, "no funcionaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" + mAuth.getCurrentUser().getProviderData().get(1).getProviderId());
                break;
            default:
                break;
        }

    }


    protected void setupNavigationDrawerContent(@NonNull NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.nav_calendar:
                                menuItem.setChecked(true);
                                setFragment(0);
                                drawer.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.nav_add_activity:
                                menuItem.setChecked(true);
                                setFragment(1);
                                drawer.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.nav_add_subj:
                                menuItem.setChecked(true);
                                setFragment(2);
                                drawer.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.nav_notes:
                                menuItem.setChecked(true);
                                setFragment(3);
                                drawer.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.nav_calculadora:
                                menuItem.setChecked(true);
                                setFragment(5);
                                drawer.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.nav_out:
                                menuItem.setChecked(true);
                                mAuth.signOut(); // Cierra la sesión pero no completamente, solo con firebase

                                mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        //
                                        if (task.isSuccessful()) {
                                            Intent activity_login = new Intent(getApplicationContext(), Activity_login.class);
                                            startActivity(activity_login);
                                            MainActivity2.this.finish();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "no se puede cerrar sesión con google",
                                                    Toast.LENGTH_LONG).show(); }
                                    }
                                });
                                setFragment(4);
                                drawer.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.nav_share:
                                menuItem.setChecked(true);
                                drawer.closeDrawer(GravityCompat.START);
                                return true;

                            case R.id.nav_help:
                                menuItem.setChecked(true);
                                String providerID = mAuth.getCurrentUser().getProviderData().get(1).getProviderId(); //"password"
                                String google="google.com";
                                if(providerID!=google) { // no se porque lo lee al contrario
                                    Toast.makeText(MainActivity2.this, "SOY AUTENTICACION  " + providerID, Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(MainActivity2.this, "SOY GOOGLE  " + providerID, Toast.LENGTH_SHORT).show();

                                }
                                drawer.closeDrawer(GravityCompat.START);
                                return true;
                        }
                        return true;
                    }
                });
    }

    public void setFragment(int position) {
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        switch (position) {
            case 0:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                CalendarActivity calendar = new CalendarActivity();
                fragmentTransaction.replace(R.id.fragment, calendar);
                fragmentTransaction.commit();
                break;
            case 1:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                Activity_add_asignatura activity_add_asignatura = new Activity_add_asignatura();
                fragmentTransaction.replace(R.id.fragment,activity_add_asignatura);
                fragmentTransaction.commit();
                break;
            case 2:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                Activity_crear crear_activity = new Activity_crear();
                fragmentTransaction.replace(R.id.fragment,crear_activity);
                fragmentTransaction.commit();
                break;
            case 3:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                NoteActivity noteActivity= new NoteActivity();
                fragmentTransaction.replace(R.id.fragment,noteActivity);
                fragmentTransaction.commit();
                break;
            case 5:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                CalculadoraActivity calculadoraActivity= new CalculadoraActivity();
                fragmentTransaction.replace(R.id.fragment,calculadoraActivity);
                fragmentTransaction.commit();
                break;

        }
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
