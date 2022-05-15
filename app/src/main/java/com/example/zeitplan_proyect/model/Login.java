package com.example.zeitplan_proyect.model;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.zeitplan_proyect.DataBase.Firebase;
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


    private static final String TAG = "Login";
    private Context mContext;
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private Firebase db = Firebase.getInstance();

    //FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();

    //private Firebase db = Firebase.getInstance();

    public Login(Context mContext) {
        this.mContext = mContext;

    }

    public void loginUser(String emailUser, String passwordUser){
        db.loginUser(emailUser,passwordUser, mContext);

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
        db.firebaseAuthWithGoogle(idToken,TAG,mContext);
    }

}
