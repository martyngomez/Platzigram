package com.martyngomez.platzigram.login.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.martyngomez.platzigram.R;
import com.martyngomez.platzigram.login.presenter.LoginPresenter;
import com.martyngomez.platzigram.login.presenter.LoginPresenterImpl;
import com.martyngomez.platzigram.view.ContainerActivity;

public class LoginActivity extends AppCompatActivity implements LoginView {
    private TextInputEditText username,password;
    private Button login;
    private ProgressBar progressBarLogin;
    private LoginPresenter presenter;

    private static final String TAG = "LoginActivity" ;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance(); // Obtiene la informacion desde json
        authStateListener = new FirebaseAuth.AuthStateListener() { //Listener que esta pendiente de lo cambios en la sesion
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {  //Si la ssion cambia ejecuta el codigo
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null){ //Valida si esta logueado
                    Log.w(TAG, "Usuario Logueado" + firebaseUser.getEmail());
                }
                else{
                    Log.w(TAG, "Usuario No Logueado" );
                }
            }
        };


        username = (TextInputEditText) findViewById(R.id.username);
        password = (TextInputEditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        progressBarLogin = (ProgressBar) findViewById(R.id.progresssBarLogin);
        hideProgressBar();

        presenter = new LoginPresenterImpl(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if (username.equals(""))
                singIn(username.getText().toString(),password.getText().toString());

            }
        });

    }

    private void singIn(String username, String password) {
        presenter.signIn(username,password, this,  firebaseAuth);
    }

    @Override
    public void enableInputs() {
        username.setEnabled(true);
        password.setEnabled(true);
        login.setEnabled(true);
    }

    @Override
    public void disableInputs() {
        username.setEnabled(false);
        password.setEnabled(false);
        login.setEnabled(false);
    }

    @Override
    public void showProgressBar() {
        progressBarLogin.setVisibility( View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBarLogin.setVisibility( View.GONE); // Oculta barra
    }

    @Override
    public void loginError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void goCreateAccount(View view) {
        Intent intent = new Intent(this, CreateAccountActivity.class); //Prmer parametro :actividad origen, seundo actividad destino
        startActivity(intent);
    }

    @Override
    public void goHome() {
        Intent intent = new Intent(this, ContainerActivity.class); //Prmer parametro :actividad origen, seundo actividad destino
        startActivity(intent);
    }

    @Override
    public void goWeb(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://www.google.com"));
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener); //Registra listener
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener); //Quita el listener
    }

}
