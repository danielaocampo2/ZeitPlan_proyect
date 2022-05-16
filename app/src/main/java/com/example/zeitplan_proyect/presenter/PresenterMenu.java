package com.example.zeitplan_proyect.presenter;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zeitplan_proyect.DataBase.Firebase;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

import de.hdodenhof.circleimageview.CircleImageView;

public class PresenterMenu {


    private Firebase db = Firebase.getInstance();
    private Context mContext;


    public PresenterMenu(Context mContext) {
        this.mContext = mContext;
    }

    //Ensayo con implementacion en Main activity 2
    public void cierraSession(){
        db.cierraSession(mContext);
    }

    public void asignarImgNom(TextView userName, CircleImageView imgvw) {
            db.asignarImgNom(mContext,userName,imgvw);
    }


}
