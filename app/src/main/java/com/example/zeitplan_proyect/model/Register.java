package com.example.zeitplan_proyect.model;

import android.content.Context;
import android.content.Intent;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.zeitplan_proyect.MainActivity2;
import com.example.zeitplan_proyect.vista.Activity_login;
import com.example.zeitplan_proyect.vista.RegistroActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Register {

    private Context mContext;
    private FirebaseAuth mAuth= FirebaseAuth.getInstance();;
    FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();


    public Register(Context mContext) {
        this.mContext = mContext;
    }

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[@#$%^&+=])" +     // at least 1 special character
                    "(?=\\S+$)" +            // no white spaces
                    ".{7,}");/* +                // at least 7 characters
                    "$");*/

    private EditText txtuserName2, txtuserName, txtpassword2, txtpassword;
    private TextInputLayout titUserName2, titUserName,titPassword2,titPassword;

    public void inicializaVariable(TextInputLayout titUserName2, EditText txtuserName2, EditText txtuserName, TextInputLayout titUserName, EditText txtpassword2,
                                    TextInputLayout titPassword2,EditText txtpassword, TextInputLayout titPassword){
        this.titUserName2=titUserName2;
        this.txtuserName2=txtuserName2;
        this.txtuserName=txtuserName;
        this.titUserName=titUserName;
        this.txtpassword2=txtpassword2;
        this.titPassword2=titPassword2;
        this.txtpassword=txtpassword;
        this.titPassword=titPassword;

    }

    public boolean verifyEmpty() {
        boolean empty = false;
        if (txtuserName2.getText().toString().isEmpty()) {
            titUserName2.setError("Campo obligatorio");
            titUserName2.setError("Campo obligatorio");
            empty = true;
        } else {
            titUserName2.setError(null);
        }
        if (txtuserName.getText().toString().isEmpty()) {
            titUserName.setError("Campo obligatorio");
            empty = true;
        }
        if (txtpassword2.getText().toString().isEmpty()) {
            titPassword2.setError("Campo obligatorio");
            empty = true;
        }
        if (txtpassword.getText().toString().isEmpty()) {
            titPassword.setError("Campo obligatorio");
            empty = true;
        }

        return empty;
    }

    public boolean validateEmail() {
        if (Patterns.EMAIL_ADDRESS.matcher(txtuserName.getText().toString()).matches() == false) {
            titUserName.setError("Correo invalido");
            return false;
        } else {
            titUserName.setError(null);
            return true;
        }
    }

    public boolean validatePassword() {
        String password = titPassword.getEditText().getText().toString().trim(); // trim elimina espacios en blanco de ambos ectremos del string

        String password2 = titPassword2.getEditText().getText().toString().trim(); // trim elimina espacios en blanco de ambos ectremos del string

        // if password does not matches to the pattern
        // it will display an error message "Password is too weak"
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            titPassword.setError("La contraseña es débil.");
            Toast.makeText(mContext, "La contraseña NO admite espacios y debe incluir al mínimo 4 caracteres. ", Toast.LENGTH_LONG).show();
            return false;
        } else if (!password.equals(password2)) {
            titPassword2.setError("Las contraseña no coincide con la primera");
            return false;
        } else {
            titPassword.setError(null);
            titPassword2.setError(null);
            return true;
        }
    }

    public void registerUser(String nameUser, String emailUser, String passwordUser) {
/*
        mAuth.createUserWithEmailAndPassword(emailUser, passwordUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(RegistroActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                    // updateUI(null);
                }
            }
        });*/

        mAuth.createUserWithEmailAndPassword(emailUser, passwordUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                String id = mAuth.getCurrentUser().getUid();
                Toast.makeText(mContext, "Usuario registrado con exito", Toast.LENGTH_SHORT).show();


                Map<String, Object> map = new HashMap<>();
                map.put("id", id);
                map.put("name", nameUser);
                map.put("email", emailUser);
                map.put("password", passwordUser);
                //Crea una collection llamada User y recibe un evento andOn..
                mFirestore.collection("user").document(id).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //Toast.makeText(mContext, "Bienvenid@", Toast.LENGTH_SHORT).show();
                        //((RegistroActivity)mContext).finish(); //Finalizamos esta actividad
                        //mContext.startActivity(new Intent(mContext, MainActivity2.class));
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

}
