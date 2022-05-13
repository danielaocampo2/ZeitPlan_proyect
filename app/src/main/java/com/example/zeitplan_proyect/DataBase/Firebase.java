package com.example.zeitplan_proyect.DataBase;

import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.zeitplan_proyect.MainActivity2;
import com.example.zeitplan_proyect.vista.RegistroActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Firebase {
    private int registro=-2;

    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();

    private static Firebase instance;

    public static Firebase getInstance(){
        if(instance == null){
            instance = new Firebase();
        }
        return instance;
    }

    public int registerUser(String nameUser, String emailUser, String passwordUser) {


        mAuth.createUserWithEmailAndPassword(emailUser, passwordUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


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
                        registro=1;
                    }
                }).addOnFailureListener(new OnFailureListener() { // en caso de que no entre correcto uestra un error
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        registro=0;
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                registro=-1;
            }
        });
        return registro;
    }

}
