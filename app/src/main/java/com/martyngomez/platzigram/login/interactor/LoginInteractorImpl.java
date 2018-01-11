package com.martyngomez.platzigram.login.interactor;

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
    public void signIn(String username, String password) {
        repository.signIn(username,password);
    }
}
