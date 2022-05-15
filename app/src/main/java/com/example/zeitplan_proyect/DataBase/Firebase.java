package com.example.zeitplan_proyect.DataBase;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.zeitplan_proyect.MainActivity2;
import com.example.zeitplan_proyect.model.Register;
import com.example.zeitplan_proyect.model.User;
import com.example.zeitplan_proyect.vista.Activity_login;
import com.example.zeitplan_proyect.vista.RegistroActivity;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Firebase {

    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
    private User usuario;
    private GoogleSignInClient mGoogleSignInClient;

    private static Firebase instance;


    public static Firebase getInstance(){
        if(instance == null){
            instance = new Firebase();
        }
        return instance;
    }

    public void registerUser(String nameUser, String emailUser, String passwordUser,Context mContext) {

        mAuth.createUserWithEmailAndPassword(emailUser, passwordUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                String id = mAuth.getCurrentUser().getUid();
                Map<String, Object> map = new HashMap<>();
                map.put("id", id);
                map.put("name", nameUser);
                map.put("email", emailUser);
                map.put("password", passwordUser);
                //Crea una collection llamada User y recibe un evento andOn..
                mFirestore.collection("user").document(id).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(mContext, "Usuario registrado con exito", Toast.LENGTH_SHORT).show();
                        Intent dashboardActivity = new Intent(mContext, MainActivity2.class);
                        mContext.startActivity(dashboardActivity);
                        ((RegistroActivity)mContext).finish();

                    }
                }).addOnFailureListener(new OnFailureListener() { // en caso de que no entre correcto uestra un error
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(mContext, "Error al guardar", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(mContext, "Error al registrar", Toast.LENGTH_SHORT).show();
            }
        });

    }



    public void loginUser(String emailUser, String passwordUser,Context mContext){
        mAuth.signInWithEmailAndPassword(emailUser,passwordUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(mContext,"Bienvenido",Toast.LENGTH_SHORT).show();
                    Intent intent =new Intent(mContext, MainActivity2.class);
                    mContext.startActivity(intent);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(mContext,"  Error al iniciar sesión",Toast.LENGTH_SHORT).show();
              }
        });

    }
    public void firebaseAuthWithGoogle(String idToken,String TAG,Context mContext) {

        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in User's information
                    Log.d(TAG, "signInWithCredential:success");
                    // FirebaseUser User = mAuth.getCurrentUser();
                    //Iniciar DASHBOARD u otra actividad luego del SigIn Exitoso
                    //ESTO LO PODEMOS MODIFICAR: Estamos en loginActivity y lo mandamos a mainActivity y terminamos la actividad en loginActivity
                    Intent dashboardActivity = new Intent(mContext, MainActivity2.class);
                    mContext.startActivity(dashboardActivity);
                    ((Activity_login)mContext).finish();

                } else {
                    // If sign in fails, display a message to the User.
                    Log.w(TAG, "signInWithCredential:failure", task.getException());
                }

            }
        });

    }

//Ensayo con implementacion en Main activity 2
    public void cierraSession(Context mContent){
        Log.i(TAG, "cierraSession: 222222222222222222222222222222");
        mAuth.signOut(); // Cierra la sesión pero no completamente, solo con firebase
        Log.i(TAG, "cierraSession: ");/*
        mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //
                if (task.isSuccessful()) {
                    Intent activity_login = new Intent(mContent, Activity_login.class);
                    mContent.startActivity(activity_login);
                    ((MainActivity2)mContent).finish();
                } else {
                    Toast.makeText(mContent, "no se puede cerrar sesión con google",
                            Toast.LENGTH_LONG).show(); }
            }
        });*/
    }
}
