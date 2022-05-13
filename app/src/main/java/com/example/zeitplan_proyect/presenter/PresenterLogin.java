package com.example.zeitplan_proyect.presenter;

import android.widget.EditText;

import com.example.zeitplan_proyect.model.Login;
import com.example.zeitplan_proyect.vista.Activity_login;
import com.google.android.material.textfield.TextInputLayout;

public class PresenterLogin {

    private Login login;
    private Activity_login activityLogin;

    public PresenterLogin(Activity_login activityLogin, Login login) {
        this.login = login;
        this.activityLogin = activityLogin;
    }

    public void loginUser(String emailUser, String passwordUser){

        login.loginUser(emailUser,passwordUser);
    }

    public boolean validatePassword(TextInputLayout inputPassword) {
       boolean status = login.validatePassword(inputPassword);
       return status;
    }

    public boolean validateEmail(EditText txtEmail , TextInputLayout inputEmail) {
        boolean status = login.validateEmail(txtEmail,inputEmail);
        return status;
    }

    public void firebaseAuthWithGoogle(String idToken,String TAG) {

        login.firebaseAuthWithGoogle(idToken,TAG);

    }
}
