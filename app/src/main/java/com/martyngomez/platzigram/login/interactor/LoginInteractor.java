package com.martyngomez.platzigram.login.interactor;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by MTN on 10/01/2018.
 */

public interface LoginInteractor {
    void signIn(String username, String password, Activity activity,FirebaseAuth firebaseAuth);

}
