package com.martyngomez.platzigram.login.repository;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
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
    public void signIn(String username, String password, Activity activity,FirebaseAuth firebaseAuth) {
        firebaseAuth.signInWithEmailAndPassword(username,password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() { // Listener atento a autenticacion
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    presenter.loginSuccess();
                }else{
                    Log.w(TAG, "Error de Login", task.getException());
                    presenter.logInError("Login Error ");
                }
            }
        });


    }
}
