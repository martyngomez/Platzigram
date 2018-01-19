package com.martyngomez.platzigram;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;

import com.facebook.FacebookSdk;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by MTN on 12/01/2018.
 */

public class PlatzigramApplication extends Application {
    private static final String TAG = "PlatzigramApplication" ;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseStorage firebaseStorage;

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseCrash.log("Inicializando variables en PlatzigramApplication");

        FacebookSdk.sdkInitialize(getApplicationContext());


        firebaseAuth = FirebaseAuth.getInstance(); // Obtiene la informacion desde json
        authStateListener = new FirebaseAuth.AuthStateListener() { //Listener que esta pendiente de lo cambios en la sesion
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {  //Si la ssion cambia ejecuta el codigo
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null){ //Valida si esta logueado
                    //Log.w(TAG, "Usuario Logueado" + firebaseUser.getEmail());
                    FirebaseCrash.logcat(Log.WARN,TAG,"Usuario Logueado" + firebaseUser.getEmail() );
                }
                else{
                    FirebaseCrash.logcat(Log.WARN,TAG,"Usuario No Logueado" );
                }
            }
        };

        firebaseStorage = FirebaseStorage.getInstance();  //Inicializa Firebase Storage

    }


    public StorageReference getStorageReference(){
        return firebaseStorage.getReference(); //URL raiz del storage
    }


}
