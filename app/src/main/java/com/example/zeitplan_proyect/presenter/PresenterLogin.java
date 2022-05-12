package com.example.zeitplan_proyect.presenter;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.hotspot2.pps.Credential;
import android.util.Log;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.zeitplan_proyect.MainActivity2;
import com.example.zeitplan_proyect.model.Login;
import com.example.zeitplan_proyect.vista.Activity_login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.concurrent.Executor;

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
