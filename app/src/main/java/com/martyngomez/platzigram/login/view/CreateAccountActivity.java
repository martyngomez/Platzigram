package com.martyngomez.platzigram.login.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.martyngomez.platzigram.R;

public class CreateAccountActivity extends AppCompatActivity {
    private static final String TAG = "CreateAccountActivity" ;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    private Button btnJoinUs;
    private TextInputEditText edtEmail, edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        showToolbar(getResources().getString(R.string.toolbar_title_createaccount),true);//Linkea con el recurso

        btnJoinUs = (Button )findViewById(R.id.joinUs);
        edtEmail = (TextInputEditText)findViewById(R.id.email);
        edtPassword = (TextInputEditText)findViewById(R.id.password_createaccount);

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

        btnJoinUs.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                createAccount();
            }
        });

    }

    private void createAccount() {
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() { //Devuelve si la opoeracion fue exitosa o no
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task ) {
                        if(task.isSuccessful()){
                            Toast.makeText(CreateAccountActivity.this, "Cuenta creada exitosamente", Toast.LENGTH_SHORT).show();
                        }else{
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(CreateAccountActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //Toast.makeText(CreateAccountActivity.this, "Error al Cuenta creada" , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    public void showToolbar(String title, boolean upButton ) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton); // Habilita boton Back
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
