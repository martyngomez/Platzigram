package com.martyngomez.platzigram.login.interactor;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;
import com.martyngomez.platzigram.login.presenter.LoginPresenter;
import com.martyngomez.platzigram.login.repository.LoginRepositoryImpl;
import com.martyngomez.platzigram.login.repository.LoginRespository;

/**
 * Created by MTN on 10/01/2018.
 */

public class LoginInteractorImpl implements LoginInteractor {
    private LoginPresenter presenter;
    private LoginRespository repository;

    public LoginInteractorImpl(LoginPresenter presenter) {
        this.presenter = presenter;
        repository = new LoginRepositoryImpl(presenter);
    }

    @Override
    public void signIn(String username, String password,Activity activity,FirebaseAuth firebaseAuth) {
        repository.signIn(username,password, activity,firebaseAuth);
    }
}
