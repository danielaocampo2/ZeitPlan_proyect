package com.example.zeitplan_proyect.vista;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.zeitplan_proyect.MainActivity2;
import com.example.zeitplan_proyect.R;
import com.example.zeitplan_proyect.model.Login;
import com.example.zeitplan_proyect.presenter.PresenterLogin;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;

import com.google.firebase.auth.FirebaseAuth;

public class Activity_login extends AppCompatActivity {

    EditText txtEmail, txtPassword;
    TextInputLayout inputEmail, inputPassword;

    PresenterLogin presentadorLogin;
    FirebaseAuth mAuth;

    //Variable para gestionar FirebaseAuth

    Button btnGoogle, btnRegistrarseLogin, btnLogin;
    //Agregar cliente de inicio de sesión de Google
    private GoogleSignInClient mGoogleSignInClient;
    //int RC_SIGN_IN=1; // constante
    String TAG = "GoogleSingInLoginActivity";
    //Variable mAuthStateListener para controlar el estado del usuario.
    private FirebaseAuth.AuthStateListener mAuthStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtEmail = findViewById(R.id.userName);
        txtPassword = findViewById(R.id.password);
        inputPassword = findViewById(R.id.titPassword);
        inputEmail = findViewById(R.id.titUserName);

        btnGoogle = findViewById(R.id.btnGoogle); // referencia boton google
        btnRegistrarseLogin = findViewById(R.id.btnRegistrarseLogin);
        btnLogin = findViewById((R.id.btnLogin));

        btnRegistrarseLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Activity_login.this, RegistroActivity.class);
                startActivity(intent);
            }
        });


        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //signIn();
                resultLauncher.launch(new Intent(mGoogleSignInClient.getSignInIntent()));
            }
        });

        // Configurar Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // Crear un GoogleSignInClient con las opciones especificadas por gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Inicializar Firebase Auth
        mAuth = FirebaseAuth.getInstance(); // para controlar el estado del usuario
        presentadorLogin = new PresenterLogin(this, new Login()) ;

        //Controlar el estado del usuario
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) { //si no es null redirigir
                    Intent intentDashboard = new Intent(getApplicationContext(), MainActivity2.class);
                    intentDashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intentDashboard);
                }
            }
        };

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!presentadorLogin.validateEmail(txtEmail ,inputEmail) | !presentadorLogin.validatePassword(inputPassword)) {
                    return;
                } else {
                    String emailUser = txtEmail.getText().toString().trim();
                    String passwordUser = txtPassword.getText().toString().trim();
                    presentadorLogin.loginUser(emailUser, passwordUser);
                }

            }
        });
    }

    ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent intent = result.getData();
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(intent);
                // si esto es verdadero lo de elegir la cuenta
                if (task.isSuccessful()) {
                    try {
                        //Se verifica que el usuario obtenga una cuenta de google y se la envie al metodo firebaseA..
                        // Google Sign In was successful, authenticate with Firebase
                        GoogleSignInAccount account = task.getResult(ApiException.class);
                        Log.d(TAG, "Genial, firebaseAuthWithGoogle:" + account.getId());
                        presentadorLogin.firebaseAuthWithGoogle(account.getIdToken(),TAG);

                    } catch (ApiException e) {
                        // Google Sign In fallido, actualizar GUI
                        Log.w(TAG, "Google sign in failed", e);
                    }
                } else {
                    Log.d(TAG, "Error,login no exitoso:" + task.getException().toString());
                }
            }
        }
    });



    //Evitamos que vuelva a login_activity con el botón atrás si ya esta logeado.
    @Override
    protected void onStart() {
        mAuth.addAuthStateListener(mAuthStateListener);
        super.onStart();
    }




}