package com.martyngomez.platzigram.login.repository;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by MTN on 10/01/2018.
 */

public interface LoginRespository {
    void signIn(String username, String password, Activity activity,FirebaseAuth firebaseAuth);

}
