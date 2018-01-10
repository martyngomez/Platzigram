package com.mardev.platzigram.login.presenter;

/**
 * Created by MTN on 10/01/2018.
 */

public interface LoginPresenter {
    void signIn(String username, String password); //Interactor
    void loginSuccess();
    void logInError(String error);

}
