package com.martyngomez.platzigram.login.repository;

import com.martyngomez.platzigram.login.presenter.LoginPresenter;

/**
 * Created by MTN on 10/01/2018.
 */

public class LoginRepositoryImpl implements LoginRespository {
    LoginPresenter presenter;

    public LoginRepositoryImpl(LoginPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void signIn(String username, String password) {
        boolean success = true;
        if (success){
            presenter.loginSuccess();
        }else{
            presenter.logInError("Login Error ");
        }
    }
}
