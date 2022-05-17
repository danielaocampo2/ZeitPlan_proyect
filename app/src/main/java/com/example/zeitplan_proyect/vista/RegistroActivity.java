package com.example.zeitplan_proyect.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.zeitplan_proyect.R;
import com.example.zeitplan_proyect.model.Register;
import com.example.zeitplan_proyect.presenter.PresenterRegister;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;


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
        txtuserName = findViewById(R.id.inputEmail);
        txtpassword = findViewById(R.id.password);
        txtpassword2 = findViewById(R.id.password2);
        titUserName2 = findViewById(R.id.titUserName2);
        titUserName = findViewById(R.id.txtEmail);
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