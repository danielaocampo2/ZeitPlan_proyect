package com.example.zeitplan_proyect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    //Variable para gestionar FirebaseAuth
    private FirebaseAuth mAuth;

    private TextView userNombre,userEmail,userID;
    private CircleImageView userImg;
    Button btnCerrarSession; // Falta pasarlo

    //Variables opcionales para cerrar sesión en  de google
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInOptions gso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userNombre = findViewById(R.id.userNombre);
        userEmail = findViewById(R.id.userEmail);
        userID = findViewById(R.id.userId);
        userImg = findViewById(R.id.userImagen);


        btnCerrarSession=findViewById(R.id.btnLogout);

        //Iniciar firebase Auth
        mAuth=FirebaseAuth.getInstance();
        FirebaseUser currentUser= mAuth.getCurrentUser();

        //Establecemos los campos
        userNombre.setText(currentUser.getDisplayName());
        userEmail.setText(currentUser.getEmail());
        userID.setText(currentUser.getUid());

        //Cargar imagen con glide
        Glide.with(this).load(currentUser.getPhotoUrl()).into(userImg);

        //Configurar las gso para google signIn con el fin de luego desloguear de google
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        btnCerrarSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut(); // Cierra la sesión pero no completamente, solo con firebase

                //Cerrar sesión con google tambien: Google sign out

                mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //
                        if(task.isSuccessful()){
                            Intent activity_login=new Intent(getApplicationContext(), com.example.zeitplan_proyect.activity_login.class);
                            startActivity(activity_login);
                            MainActivity.this.finish();
                        }else{
                            Toast.makeText(getApplicationContext(),"no se puede cerrar sesión con google",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });
    }
}