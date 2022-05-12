package com.example.zeitplan_proyect.model;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.zeitplan_proyect.MainActivity2;
import com.example.zeitplan_proyect.vista.Activity_login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class Login {

    private Context mContext;
    private FirebaseAuth mAuth;
    //FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();



    public void loginUser(String emailUser, String passwordUser){
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
                Toast.makeText(mContext,"Error al iniciar sesión",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public boolean validatePassword(TextInputLayout inputPassword) {
        String passwordInput = inputPassword.getEditText().getText().toString().trim(); // trim elimina espacios en blanco de ambos ectremos del string
        // if password field is empty
        // it will display error message "Field can not be empty"
        if (passwordInput.isEmpty()) {
            inputPassword.setError("Campo obligatorio");
            return false;
        } else {
            inputPassword.setError(null);
            return true;
        }
    }
    public boolean validateEmail(EditText txtEmail , TextInputLayout inputEmail) {
        if (Patterns.EMAIL_ADDRESS.matcher(txtEmail.getText().toString()).matches() == false) {
            if (txtEmail.getText().toString().isEmpty()) {
                inputEmail.setError("Campo obligatorio");
                return false;
            } else {
                inputEmail.setError("Correo invalido");
                return false;
            }
        } else {
            inputEmail.setError(null);
            return true;
        }
    }

    public void firebaseAuthWithGoogle(String idToken,String TAG) {


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
}
