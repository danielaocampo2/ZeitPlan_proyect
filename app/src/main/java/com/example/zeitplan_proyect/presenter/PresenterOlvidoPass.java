package com.example.zeitplan_proyect.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.EditText;

import com.example.zeitplan_proyect.model.Login;
import com.google.android.material.textfield.TextInputLayout;

public class PresenterOlvidoPass {

    private static final String TAG = "presenterOlvido";
    Login login;

    public PresenterOlvidoPass(Login login) {
        this.login = login;
    }

    public boolean validateEmail(EditText txtEmail , TextInputLayout inputEmail) {
        boolean status = login.validateEmail(txtEmail,inputEmail);
        return status;
    }

    public void sendEmail(String email){

        login.sendPassword(email);
    }
}
