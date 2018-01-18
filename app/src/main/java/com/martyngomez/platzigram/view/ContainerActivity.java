package com.martyngomez.platzigram.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.login.LoginManager;
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

        firebaseInitializa();

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

    private void firebaseInitializa(){
        firebaseAuth = FirebaseAuth.getInstance(); // Obtiene la informacion desde json
       // authStateListener = new FirebaseAuth.AuthStateListener() { //Listener que esta pendiente de lo cambios en la sesion
            // @Override
         //   public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {  //Si la ssion cambia ejecuta el codigo
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) { //Valida si esta logueado
                    Log.w(TAG, "Usuario Logueado" + firebaseUser.getEmail());

                } else {
                    Log.w(TAG, "Usuario No Logueado");
                    goLogin();
                }
           // }
       // };
    }



    public void goLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //Agrega Menu
        getMenuInflater().inflate(R.menu.menu_opciones,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Captura accion en menu
        switch (item.getItemId()){
            case R.id.mSignnOut:
                firebaseAuth.signOut();

                if (LoginManager.getInstance()!= null){
                      LoginManager.getInstance().logOut();
                }

                Toast.makeText(this, "Sesion cerrada", Toast.LENGTH_SHORT).show();
                goLogin();

                break;
            case R.id.mAbout:
                Toast.makeText(this, "Platzigram by martyngomez form Platzi", Toast.LENGTH_SHORT).show();

        }
        return super.onOptionsItemSelected(item);
    }
}
