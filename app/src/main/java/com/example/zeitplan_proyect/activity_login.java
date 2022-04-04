package com.example.zeitplan_proyect;

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
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class activity_login extends AppCompatActivity {

    //Variable para gestionar FirebaseAuth
    private FirebaseAuth mAuth;
    Button btnGoogle;
    //Agregar cliente de inicio de sesión de Google
    private GoogleSignInClient mGoogleSignInClient;
    //int RC_SIGN_IN=1; // constante
    String TAG ="GoogleSingInLoginActivity";
    //Variable mAuthStateListener para controlar el estado del usuario.
    private FirebaseAuth.AuthStateListener mAuthStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnGoogle = findViewById(R.id.btnGoogle); // referencia boton google


        btnGoogle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
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

        //Controlar el estado del usuario
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null){ //si no es null redirigir
                     Intent intentDashboard = new Intent(getApplicationContext(), MainActivity.class);
                     intentDashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                     startActivity(intentDashboard);
                }
            }
        };
    }

    ActivityResultLauncher<Intent> resultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode()==Activity.RESULT_OK){
                Intent intent =result.getData();
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(intent);
                // si esto es verdadero lo de elegir la cuenta
                if(task.isSuccessful()){ try {
                    //Se verifica que el usuario obtenga una cuenta de google y se la envie al metodo firebaseA..
                    // Google Sign In was successful, authenticate with Firebase
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                    firebaseAuthWithGoogle(account.getIdToken());
                } catch (ApiException e) {
                    // Google Sign In fallido, actualizar GUI
                    Log.w(TAG, "Google sign in failed", e);
                }
                }else{
                    Log.d(TAG, "Error, login no exitoso:" + task.getException().toString());
                    //PARA LOS ERRORES-- Usuario se sale del menu de elegir cuenta de google
                  //  Toast.makeText(this,"Ocurrio un error."+task.getException().toString(),Toast.LENGTH_LONG).show();
                }

            }
        }
    });
    /*
    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Resultado devuelto al iniciar el Intent de GoogleSignInApi.getSignInIntent (...);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            // si esto es verdadero lo de elegir la cuenta
            if(task.isSuccessful()){ try {
                //Se verifica que el usuario obtenga una cuenta de google y se la envie al metodo firebaseA..
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In fallido, actualizar GUI
                Log.w(TAG, "Google sign in failed", e);
            }
            }else{
                Log.d(TAG, "Error, login no exitoso:" + task.getException().toString());
                //PARA LOS ERRORES-- Usuario se sale del menu de elegir cuenta de google
                Toast.makeText(this, "Ocurrio un error. "+task.getException().toString(),
                        Toast.LENGTH_LONG).show();
            }
        }
    }*/


    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);

        //Cuando se envian las credenciales, llamamos a addOnComplete..
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    //variable task de tipo autresult, que controla si es o no exitoso el login
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            // FirebaseUser user = mAuth.getCurrentUser();
                            //Iniciar DASHBOARD u otra actividad luego del SigIn Exitoso
                            //ESTO LO PODEMOS MODIFICAR: Estamos en loginActivity y lo mandamos a mainActivity y terminamos la actividad en loginActivity
                            Intent dashboardActivity = new Intent(activity_login.this, MainActivity.class);
                            startActivity(dashboardActivity);
                            activity_login.this.finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                        }
                    }});
    }

    /* LLama a un metodo llamado startActivity.. para recibir un resultado
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }*/


    //Evitamos que vuelva a login_activity con el botón atrás si ya esta logeado.
    @Override protected void onStart() {
        mAuth.addAuthStateListener(mAuthStateListener);
        super.onStart();
    }





}