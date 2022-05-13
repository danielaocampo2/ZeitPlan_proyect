package com.example.zeitplan_proyect.presenter;

import android.widget.EditText;
import android.widget.Toast;

import com.example.zeitplan_proyect.model.Register;
import com.example.zeitplan_proyect.vista.RegistroActivity;
import com.google.android.material.textfield.TextInputLayout;

public class PresenterRegister {

    private RegistroActivity registerActivity;
    private Register register;

    public PresenterRegister(RegistroActivity registerActivity, Register register) {
        this.registerActivity = registerActivity;
        this.register = register;
    }

    public void inicializaVariable(TextInputLayout titUserName2, EditText txtuserName2, EditText txtuserName, TextInputLayout titUserName, EditText txtpassword2,
                                    TextInputLayout titPassword2,EditText txtpassword, TextInputLayout titPassword){
        register.inicializaVariable( titUserName2, txtuserName2, txtuserName,  titUserName,  txtpassword2, titPassword2, txtpassword,  titPassword);

    }

    public boolean verifyEmpty(){
        boolean status=register.verifyEmpty();
        return status;
    }
    public boolean validateEmail() {
        boolean status=register.validateEmail();
        return status;
    }
    public boolean validatePassword() {
        boolean status=register.validatePassword();
        return status;
    }

    public void registerUser(String nameUser, String emailUser, String passwordUser){
        register.registerUser(nameUser,emailUser,passwordUser);
    }
}
