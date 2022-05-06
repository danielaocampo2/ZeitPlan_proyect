package com.example.zeitplan_proyect.userAdapter;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class userAdapter extends Activity {

    public static FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
    public static final String TAG = "userAdapter";
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();




}
