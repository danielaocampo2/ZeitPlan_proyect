package com.example.zeitplan_proyect.presenter;

import android.content.Context;
import android.content.Intent;
import android.util.Patterns;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.zeitplan_proyect.MainActivity2;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class PresenterLogin {

    private Context mContext;
    private FirebaseAuth mAuth;
    //FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();

    public PresenterLogin(Context mContext, FirebaseAuth mAuth) {
        this.mContext = mContext;
        this.mAuth = mAuth;
        //this.mFirestore = mFirestore;
    }

    public void loginUser(String emailUser, String passwordUser){
        mAuth.signInWithEmailAndPassword(emailUser,passwordUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    Toast.makeText(mContext,"Bienvenido",Toast.LENGTH_SHORT).show();
                    Intent intent =new Intent(mContext,MainActivity2.class);
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
    private boolean validatePassword(TextInputLayout inputPassword) {
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
   /* private boolean validateEmail() {
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
    }*/
}
