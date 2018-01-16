package com.martyngomez.platzigram.login.repository;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.martyngomez.platzigram.login.presenter.LoginPresenter;

/**
 * Created by MTN on 10/01/2018.
 */

public class LoginRepositoryImpl implements LoginRespository {
    private static final String TAG = "LoginRepositoryImpl" ;
    LoginPresenter presenter;

    public LoginRepositoryImpl(LoginPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void signIn(String username, String password, final Activity activity,FirebaseAuth firebaseAuth) {
        firebaseAuth.signInWithEmailAndPassword(username,password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() { // Listener atento a autenticacion
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser user = task.getResult().getUser(); //trae datos del usuario desde Firebase
                    //Modo privado accesibel solo par ala app
                    SharedPreferences preferences = activity.getSharedPreferences("USER", Context.MODE_PRIVATE );
                    SharedPreferences.Editor editor = preferences.edit(); //Abre Shares Preferences
                    editor.putString("email", user.getEmail()); //Inserta en XML
                    editor.commit(); //Cierra operacion

                    presenter.loginSuccess();
                }else{
                    Log.w(TAG, "Error de Login", task.getException());
                    presenter.logInError("Login Error ");
                }
            }
        });


    }
}
