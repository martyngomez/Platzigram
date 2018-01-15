package com.martyngomez.platzigram.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.martyngomez.platzigram.R;
import com.martyngomez.platzigram.login.view.LoginActivity;
import com.martyngomez.platzigram.post.view.HomeFragment;
import com.martyngomez.platzigram.view.fragment.ProfileFragment;
import com.martyngomez.platzigram.view.fragment.SearchFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class ContainerActivity extends AppCompatActivity {
    private static final String TAG = "ContainerActivity" ;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container); //Relaciona con layout

        BottomBar bottonBar = findViewById(R.id.bottombar);
        bottonBar.setDefaultTab(R.id.home); //Fragment por defecto

        firebaseAuth = FirebaseAuth.getInstance(); // Obtiene la informacion desde json
       // authStateListener = new FirebaseAuth.AuthStateListener() { //Listener que esta pendiente de lo cambios en la sesion
           // @Override
          //  public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {  //Si la ssion cambia ejecuta el codigo
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null){ //Valida si esta logueado
                    Log.w(TAG, "Usuario Logueado" + firebaseUser.getEmail());

                }
                else{
                    Log.w(TAG, "Usuario No Logueado" );
                    goLogin();//Si No esta logueado va a login.
                }
          //  }
      //  };

        bottonBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                switch (tabId){
                    case R.id.home:
                        HomeFragment homeFragment = new HomeFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment) //(Donde se inserta el fragment, el fragment a insertar )
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE) // Selecciona transicion
                                .addToBackStack(null).commit(); // Por las transacciones,, // Con commit basta
                        break;
                    case R.id.profile:
                        ProfileFragment profileFragment = new ProfileFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, profileFragment) //(Donde se inserta el fragment, el fragment a insertar )
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE) // Selecciona transicion
                                .addToBackStack(null).commit(); // Por las transacciones,, // Con commit basta
                        break;
                    case R.id.search:
                        SearchFragment searchFragment = new SearchFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, searchFragment) //(Donde se inserta el fragment, el fragment a insertar )
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE) // Selecciona transicion
                                .addToBackStack(null).commit(); // Por las transacciones,, // Con commit basta
                        break;
                }

            }
        });


    }


    public void goLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
