package com.example.zeitplan_proyect.DataBase;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zeitplan_proyect.model.Asignatura;
import com.example.zeitplan_proyect.model.ListaAsignatura;
import com.example.zeitplan_proyect.presenter.PresenterAsignatura;
import com.google.firebase.firestore.DocumentSnapshot;

import androidx.annotation.NonNull;

import com.example.zeitplan_proyect.R;
import com.example.zeitplan_proyect.MainActivity2;
import com.example.zeitplan_proyect.model.User;
import com.example.zeitplan_proyect.vista.Activity_login;
import com.example.zeitplan_proyect.vista.RegistroActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Firebase {

    //Variable para gestionar FirebaseAuth

    public FirebaseAuth mAuth=FirebaseAuth.getInstance();
    public FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
    public User usuario = User.getInstance();
    //PresenterAsignatura presenterAsignatura = new PresenterAsignatura();
    //CollectionReference user = mFirestore.collection("user");

    //Variables opcionales para cerrar sesión en  de google
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInOptions gso;

    private static Firebase instance;


    public static Firebase getInstance(){
        if(instance == null){
            instance = new Firebase();
        }
        return instance;
    }

    public void registerUser(String nameUser, String emailUser, String passwordUser,Context mContext) {

        mAuth.createUserWithEmailAndPassword(emailUser, passwordUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                String id = mAuth.getCurrentUser().getUid();

                usuario.setName(nameUser);
                usuario.setEmail(emailUser);
                usuario.setId(id);

                Map<String, Object> map = new HashMap<>();
                map.put("id", id);
                map.put("name", nameUser);
                map.put("email", emailUser);
                //map.put("password", passwordUser);
                //Crea una collection llamada User y recibe un evento andOn..
                mFirestore.collection("user").document(id).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(mContext, "Usuario registrado con exito", Toast.LENGTH_SHORT).show();
                        Intent dashboardActivity = new Intent(mContext, MainActivity2.class);
                        mContext.startActivity(dashboardActivity);
                        ((RegistroActivity)mContext).finish();

                    }
                }).addOnFailureListener(new OnFailureListener() { // en caso de que no entre correcto uestra un error
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(mContext, "Error al guardar", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(mContext, "Error al registrar", Toast.LENGTH_SHORT).show();
            }
        });

    }



    public void loginUser(String emailUser, String passwordUser,Context mContext){
        mAuth.signInWithEmailAndPassword(emailUser,passwordUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    String id= getIdUser();
                    usuario.setEmail(emailUser);
                    usuario.setId(id);
                    mFirestore.collection("user").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                usuario.setName(documentSnapshot.getString("name"));
                                Toast.makeText(mContext,"Bienvenido",Toast.LENGTH_SHORT).show();
                                Intent intent =new Intent(mContext, MainActivity2.class);
                                mContext.startActivity(intent);
                            }
                        }
                    });



                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(mContext,"  Error al iniciar sesión",Toast.LENGTH_SHORT).show();
              }
        });

    }
    public void firebaseAuthWithGoogle(String idToken,String TAG,Context mContext) {

        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in User's information
                    Log.d(TAG, "signInWithCredential:success");
                    // FirebaseUser User = mAuth.getCurrentUser();
                    //Iniciar DASHBOARD u otra actividad luego del SigIn Exitoso
                    //ESTO LO PODEMOS MODIFICAR: Estamos en loginActivity y lo mandamos a mainActivity y terminamos la actividad en loginActivity
                    usuario.setName(mAuth.getCurrentUser().getDisplayName());
                    usuario.setEmail(mAuth.getCurrentUser().getEmail());
                    usuario.setId(getIdUser());

                    mFirestore.collection("user").document(getIdUser()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (!documentSnapshot.exists()) {

                                Map<String, Object> map = new HashMap<>();
                                map.put("id", getIdUser());
                                map.put("name", mAuth.getCurrentUser().getDisplayName());
                                map.put("email", mAuth.getCurrentUser().getEmail());
                                //map.put("password", passwordUser);
                                //Crea una collection llamada User y recibe un evento andOn..
                                mFirestore.collection("user").document(getIdUser()).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(mContext, "Usuario registrado con exito", Toast.LENGTH_SHORT).show();


                                    }
                                }).addOnFailureListener(new OnFailureListener() { // en caso de que no entre correcto uestra un error
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(mContext, "Error al guardar", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    });

                    Intent dashboardActivity = new Intent(mContext, MainActivity2.class);
                    mContext.startActivity(dashboardActivity);
                    ((Activity_login)mContext).finish();

                } else {
                    // If sign in fails, display a message to the User.
                    Log.w(TAG, "signInWithCredential:failure", task.getException());
                }

            }
        });

    }

//Ensayo con implementacion en Main activity 2
    public void cierraSession(Context mContext){
        configuraGSO( mContext);

        mAuth.signOut(); // Cierra la sesión pero no completamente, solo con firebase
        mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //
                if (task.isSuccessful()) {
                    Intent activity_login = new Intent(mContext, Activity_login.class);
                    mContext.startActivity(activity_login);
                    ((MainActivity2)mContext).finish();
                } else {
                    Toast.makeText(mContext, "no se puede cerrar sesión con google",
                    Toast.LENGTH_LONG).show(); }
            }
        });
    }

    private void configuraGSO(Context mContext){
        //Configurar las gso para google signIn con el fin de luego desloguear de google
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(mContext.getResources().getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(mContext, gso);
    }


    public void asignarImgNom(Context mContext , TextView userName, CircleImageView imgvw) {
        String providerID = mAuth.getCurrentUser().getProviderData().get(1).getProviderId();
        if(usuario.name==null){
            String id =getIdUser();
            usuario.setId(id);
            mFirestore.collection("user").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        usuario.setName(documentSnapshot.getString("name"));
                        userName.setText(usuario.name);
                        return;
                    }
                }
            });
        }
            userName.setText(usuario.name);

        if (providerID.equals("google.com")) {
            //userName.setText(mAuth.getCurrentUser().getDisplayName());
            Glide.with(mContext).load(mAuth.getCurrentUser().getPhotoUrl()).into(imgvw);
        }
    }


    // retorna Id de usuario actual y null si no hay usuario actual

    public String getIdUser(){
        String id;
        FirebaseUser user = mAuth.getCurrentUser();
        if (user==null){
            id=null;
        }else{
            id=mAuth.getCurrentUser().getUid();
        }
        return id;
    }












}


