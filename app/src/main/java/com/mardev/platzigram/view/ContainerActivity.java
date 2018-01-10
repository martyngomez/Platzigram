package com.mardev.platzigram.view;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.mardev.platzigram.R;
import com.mardev.platzigram.post.view.HomeFragment;
import com.mardev.platzigram.view.fragment.ProfileFragment;
import com.mardev.platzigram.view.fragment.SearchFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class ContainerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container); //Relaciona con layout

        BottomBar bottonBar = findViewById(R.id.bottombar);
        bottonBar.setDefaultTab(R.id.home); //Fragment por defecto

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
}
