package com.martyngomez.platzigram.login.presenter;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by MTN on 10/01/2018.
 */

public interface LoginPresenter {
    void signIn(String username, String password, Activity activity,FirebaseAuth firebaseAuth); //Interactor
    void loginSuccess();
    void logInError(String error);

}
