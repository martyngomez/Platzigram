package com.martyngomez.platzigram.login.view;

import android.view.View;

/**
 * Created by MTN on 09/01/2018.
 * Metodos sobre los views del layout
 */

public interface LoginView {
    void enableInputs();
    void disableInputs();

    void showProgressBar();
    void hideProgressBar();

    void loginError(String error);

    void goCreateAccount(View view);
    void goHome ();
    void goWeb(View view);
}
