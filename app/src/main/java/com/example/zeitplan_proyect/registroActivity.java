package com.example.zeitplan_proyect;

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

import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;


public class registroActivity extends AppCompatActivity {

    TextView goLogin;
    Button btnRegistro;
    EditText txtuserName2,txtuserName, txtpassword, txtpassword2;
    TextInputLayout titUserName2, titUserName, titPassword, titPassword2;
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
        goLogin=findViewById(R.id.goLogin);

        txtuserName2=findViewById(R.id.userName2);
        txtuserName=findViewById(R.id.userName);
        txtpassword=findViewById(R.id.password);
        txtpassword2=findViewById(R.id.password2);
        titUserName2=findViewById(R.id.titUserName2);
        titUserName=findViewById(R.id.titUserName);
        titPassword=findViewById(R.id.titPassword);
        titPassword2=findViewById(R.id.titPassword2);

        btnRegistro=findViewById(R.id.btnRegistro);

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(verifyEmpty() | !validateEmail() | !validatePassword()){
                    return;
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

    private boolean verifyEmpty(){
        boolean empty=false;
        if(txtuserName2.getText().toString().isEmpty()){
            titUserName2.setError("Campo obligatorio");
            titUserName2.setError("Campo obligatorio");
            empty= true;
        }
        else{
            titUserName2.setError(null);
        }
        if(txtuserName.getText().toString().isEmpty()){
            titUserName.setError("Campo obligatorio");
            empty= true;
        }
        if(txtpassword2.getText().toString().isEmpty()){
            titPassword2.setError("Campo obligatorio");
            empty= true;
        }
        if(txtpassword.getText().toString().isEmpty()){
            titPassword.setError("Campo obligatorio");
            empty= true;
        }

        return empty;
    }
    private boolean validateEmail() {
        if (Patterns.EMAIL_ADDRESS.matcher(txtuserName.getText().toString()).matches()==false){
                titUserName.setError("Correo invalido");
                return false;
        }
        else{
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
        }
        else if(!password.equals(password2)) {
            titPassword2.setError("Las contraseña no coincide con la primera");
            return false;
        }
        else {
            titPassword.setError(null);
            titPassword2.setError(null);
            return true;
        }
    }

}