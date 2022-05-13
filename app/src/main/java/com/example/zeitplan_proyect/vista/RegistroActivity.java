package com.example.zeitplan_proyect.vista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zeitplan_proyect.MainActivity2;
import com.example.zeitplan_proyect.R;
import com.example.zeitplan_proyect.model.Register;
import com.example.zeitplan_proyect.presenter.PresenterLogin;
import com.example.zeitplan_proyect.presenter.PresenterRegister;
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



public class RegistroActivity extends AppCompatActivity {

    PresenterRegister presentadorRegister;


    TextView goLogin;
    Button btnRegistro;
    EditText txtuserName2, txtuserName, txtpassword, txtpassword2;
    TextInputLayout titUserName2, titUserName, titPassword, titPassword2;

    FirebaseAuth mAuth;

    private static final String TAG = "RegistroActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        goLogin = findViewById(R.id.goLogin);

        //mFirestore = FirebaseFirestore.getInstance();
        //mAuth = FirebaseAuth.getInstance();

        presentadorRegister =new PresenterRegister(this, new Register(this));
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
                presentadorRegister.inicializaVariable( titUserName2,  txtuserName2,  txtuserName,  titUserName,  txtpassword2,
                        titPassword2, txtpassword,  titPassword);
                if (presentadorRegister.verifyEmpty() | !presentadorRegister.validateEmail() | !presentadorRegister.validatePassword()) {
                    return;
                } else {

                    presentadorRegister.registerUser(name, correo, password);
                }


            }
        });

        goLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   Intent intent = new Intent(RegistroActivity.this, Activity_login.class);
                   startActivity(intent);
            }
        });

    }



    }