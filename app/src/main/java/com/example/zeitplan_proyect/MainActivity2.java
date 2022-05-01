package com.example.zeitplan_proyect;

import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity2  extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //Variable para gestionar FirebaseAuth
    private FirebaseAuth mAuth;

    //Variables opcionales para cerrar sesión en  de google
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInOptions gso;
    //private static final String TAG = "MainActivity2";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);


        //inflate header layout
        View navView =  navigationView.inflateHeaderView(R.layout.nav_header);

        //reference to views
        CircleImageView imgvw = (CircleImageView)navView.findViewById(R.id.imageView);
        TextView userName = (TextView)navView.findViewById(R.id.textView1);
        //set views
        //  imgvw.setImageResource(R.drawable.your_image);

        //Iniciar firebase Auth
        mAuth=FirebaseAuth.getInstance();
        FirebaseUser currentUser= mAuth.getCurrentUser();
        userName.setText(currentUser.getDisplayName());

        Glide.with(this).load(currentUser.getPhotoUrl()).into(imgvw);

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



    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Intent intent = null;


        if (id == R.id.nav_calendar) {
            intent = new Intent(getApplicationContext(), calendarActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_add_activity) {
            intent = new Intent(getApplicationContext(), activity_add_asignatura.class);
            startActivity(intent);
        } else if (id == R.id.nav_add_subj) {
            intent = new Intent(getApplicationContext(), activity_crear.class);
            startActivity(intent);
        } else if (id == R.id.nav_notes) {
            intent = new Intent(getApplicationContext(), NoteActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_out) {

            mAuth.signOut(); // Cierra la sesión pero no completamente, solo con firebase

            mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    //
                    if (task.isSuccessful()) {
                        Intent activity_login = new Intent(getApplicationContext(), com.example.zeitplan_proyect.activity_login.class);
                        startActivity(activity_login);
                        MainActivity2.this.finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "no se puede cerrar sesión con google",
                                Toast.LENGTH_LONG).show(); }
                }
            });
          //  }

        } else if (id == R.id.nav_help) {
            /*
            String providerID = mAuth.getCurrentUser().getProviderData().get(1).getProviderId(); //"password"
            String google="google.com";
            if(providerID!=google) {
                Toast.makeText(MainActivity2.this, "SOY AUTENTICACION  " + providerID, Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(MainActivity2.this, "SOY GOOGLE  " + providerID, Toast.LENGTH_SHORT).show();

            }*/
        }else if (id == R.id.nav_share) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
