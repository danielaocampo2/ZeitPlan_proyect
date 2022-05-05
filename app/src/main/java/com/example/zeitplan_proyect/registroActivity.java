package com.example.zeitplan_proyect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;



public class registroActivity extends AppCompatActivity {

    TextView goLogin;
    Button btnRegistro;
    EditText txtuserName2, txtuserName, txtpassword, txtpassword2;
    TextInputLayout titUserName2, titUserName, titPassword, titPassword2;
    FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();

    FirebaseAuth mAuth;

    private static final String TAG = "registroActivity";

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[@#$%^&+=])" +     // at least 1 special character
                    "(?=\\S+$)" +            // no white spaces
                    ".{4,}");/* +                // at least 4 characters
                    "$");*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        goLogin = findViewById(R.id.goLogin);

        //mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();


        txtuserName2 = findViewById(R.id.userName2);
        txtuserName = findViewById(R.id.userName);
        txtpassword = findViewById(R.id.password);
        txtpassword2 = findViewById(R.id.password2);
        titUserName2 = findViewById(R.id.titUserName2);
        titUserName = findViewById(R.id.titUserName);
        titPassword = findViewById(R.id.titPassword);
        titPassword2 = findViewById(R.id.titPassword2);

        btnRegistro = findViewById(R.id.btnRegistro);

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String password = titPassword.getEditText().getText().toString().trim();
                String name = txtuserName2.getText().toString().trim();
                String correo = txtuserName.getText().toString().trim();
                if (verifyEmpty() | !validateEmail() | !validatePassword()) {
                    return;
                } else {

                    registerUser(name, correo, password);
                }


            }
        });

        goLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(registroActivity.this, activity_login.class);
                startActivity(intent);
            }
        });

    }

    private boolean verifyEmpty() {
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

    private boolean validateEmail() {
        if (Patterns.EMAIL_ADDRESS.matcher(txtuserName.getText().toString()).matches() == false) {
            titUserName.setError("Correo invalido");
            return false;
        } else {
            titUserName.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String password = titPassword.getEditText().getText().toString().trim(); // trim elimina espacios en blanco de ambos ectremos del string

        String password2 = titPassword2.getEditText().getText().toString().trim(); // trim elimina espacios en blanco de ambos ectremos del string

        // if password does not matches to the pattern
        // it will display an error message "Password is too weak"
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            titPassword.setError("La contraseña es débil.");
            Toast.makeText(this, "La contraseña NO admite espacios y debe incluir al mínimo 4 caracteres. ", Toast.LENGTH_LONG).show();
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

    private void registerUser(String nameUser, String emailUser, String passwordUser) {

        mAuth.createUserWithEmailAndPassword(emailUser, passwordUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
              /*  if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(registroActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                   // updateUI(null);
                }*/

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
                        finish(); //Finalizamos esta actividad
                        startActivity(new Intent(registroActivity.this, MainActivity2.class));
                        Toast.makeText(registroActivity.this, "Usuario registrado con exito", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() { // en caso de que no entre correcto uestra un error
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(registroActivity.this, "Error al guardar", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(registroActivity.this, "Error al registrar", Toast.LENGTH_SHORT).show();

            }
        });/*
            }
        });*/
        }

    }