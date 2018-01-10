package com.mardev.platzigram.login.presenter;

import com.mardev.platzigram.login.interactor.LoginInteractor;
import com.mardev.platzigram.login.interactor.LoginInteractorImpl;
import com.mardev.platzigram.login.view.LoginView;

/**
 * Created by MTN on 10/01/2018.
 */

public class LoginPresenterImpl implements LoginPresenter {
    private LoginView loginView;
    private LoginInteractor interactor;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        interactor = new LoginInteractorImpl(this);
    }

    @Override
    public void signIn(String username, String password) {
        loginView.disableInputs();
        loginView.showProgressBar();
        interactor.signIn(username,password);
    }

    @Override
    public void loginSuccess() {
        loginView.goHome();
        loginView.hideProgressBar();
    }

    @Override
    public void logInError( String error) {
        loginView.enableInputs();
        loginView.hideProgressBar();
        loginView.loginError(error);

    }
}
