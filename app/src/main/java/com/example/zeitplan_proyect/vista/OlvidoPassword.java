package com.example.zeitplan_proyect.vista;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.zeitplan_proyect.R;
import com.example.zeitplan_proyect.model.Login;
import com.example.zeitplan_proyect.presenter.PresenterLogin;
import com.example.zeitplan_proyect.presenter.PresenterOlvidoPass;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class OlvidoPassword extends AppCompatActivity {

    Button btnRecuperar;

    EditText txtEmail;
    TextInputLayout inputEmail;
    PresenterOlvidoPass presentesOlvidoPass;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olvido_password);

        btnRecuperar=findViewById(R.id.btnRecuperar);
        txtEmail = findViewById(R.id.inputEmail);
        inputEmail = findViewById(R.id.txtEmail);
        presentesOlvidoPass =new PresenterOlvidoPass(new Login(this)) ;

        btnRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validar();
            }
        });

    }

    public void validar(){
        boolean status= presentesOlvidoPass.validateEmail(txtEmail ,inputEmail);
        if(status){
            String email= txtEmail.getText().toString().trim();
                presentesOlvidoPass.sendEmail(email);
        }
    }
//La flecha de la parte de abajo valida para volver atras
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(OlvidoPassword.this,Activity_login.class);
        startActivity(intent);
        finish();
    }




}