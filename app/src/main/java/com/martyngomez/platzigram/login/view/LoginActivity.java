package com.martyngomez.platzigram.login.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.martyngomez.platzigram.R;
import com.martyngomez.platzigram.login.presenter.LoginPresenter;
import com.martyngomez.platzigram.login.presenter.LoginPresenterImpl;
import com.martyngomez.platzigram.view.ContainerActivity;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity implements LoginView {
    private TextInputEditText username,password;
    private Button login;
    private LoginButton LoginButtonFacebook;
    private ProgressBar progressBarLogin;
    private LoginPresenter presenter;

    private static final String TAG = "LoginActivity" ;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager= CallbackManager.Factory.create();

        firebaseAuth = FirebaseAuth.getInstance(); // Obtiene la informacion desde json
        authStateListener = new FirebaseAuth.AuthStateListener() { //Listener que esta pendiente de lo cambios en la sesion
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {  //Si la ssion cambia ejecuta el codigo
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null){ //Valida si esta logueado
                    Log.w(TAG, "Usuario Logueado" + firebaseUser.getEmail());
                    goHome();//Si esta logueado no va a login
                }
                else{
                    Log.w(TAG, "Usuario No Logueado" );
                }
            }
        };


        username = (TextInputEditText) findViewById(R.id.username);
        password = (TextInputEditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        LoginButtonFacebook =(LoginButton) findViewById(R.id.login_facebook);
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


        LoginButtonFacebook.setReadPermissions(Arrays.asList("email")); // Permisos en facebook
        LoginButtonFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() { // Listener facebook
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.w(TAG, "Facebook Login Success: " + loginResult.getAccessToken().getApplicationId());
                signInFacebookFirebase(loginResult.getAccessToken());

            }

            @Override
            public void onCancel() {
                Log.w(TAG, "Facebook Login Cancelled. ");

            }

            @Override
            public void onError(FacebookException error) {
                Log.w(TAG, "Facebook Login Error : " + error.toString());
                error.printStackTrace();
            }
        });

    }

    private void signInFacebookFirebase(AccessToken accessToken) {
        AuthCredential authCredential = FacebookAuthProvider.getCredential(accessToken.getToken());  // Crea credencial valida para logear
        //signInWithCredential autentica adenmas otros proveedores como gmail
        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    FirebaseUser user = task.getResult().getUser(); //trae datos del usuario desde Firebase
                    SharedPreferences preferences = getSharedPreferences("USER", Context.MODE_PRIVATE );
                    SharedPreferences.Editor editor = preferences.edit(); //Abre Shares Preferences
                    editor.putString("email", user.getEmail()); //Inserta en XML
                    editor.commit(); //Cierra operacion


                    goHome();
                    hideProgressBar();
                }else{
                    enableInputs();
                    hideProgressBar();
                    loginError(task.getException().toString());
                }
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
        Intent intent = new Intent(this, ContainerActivity.class);
        startActivity(intent);
        finish();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { // Pasa estado de Fa cebook a Firebase
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

}
