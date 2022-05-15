package com.example.zeitplan_proyect.model;

import android.content.Context;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zeitplan_proyect.DataBase.Firebase;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class Register {

    private Context mContext;

    private Firebase db = Firebase.getInstance();

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


        db.registerUser(nameUser,emailUser,passwordUser,mContext);


    }


}
