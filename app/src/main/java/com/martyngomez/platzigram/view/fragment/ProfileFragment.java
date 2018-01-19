package com.martyngomez.platzigram.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.crash.FirebaseCrash;
import com.martyngomez.platzigram.R;
import com.martyngomez.platzigram.adapter.PictureAdapterRecyclerView;
import com.martyngomez.platzigram.model.Picture;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private static final String TAG = "ProfileFragment";
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    TextView logOut;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FirebaseCrash.log("Inicializando " + TAG);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        showToolbar("",false, view);

        RecyclerView picturesRecycler = (RecyclerView)view.findViewById(R.id.pictureProfileRecycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext()); //Layout Manager
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        picturesRecycler.setLayoutManager(linearLayoutManager);

        PictureAdapterRecyclerView pictureAdapterRecyclerView = new PictureAdapterRecyclerView(buildPictures(),R.layout.cardview_picure, getActivity());

        picturesRecycler.setAdapter(pictureAdapterRecyclerView);

        Log.w("Progile", "Profile test" );
        logOut = (TextView) view.findViewById(R.id.profileLogout);

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if (username.equals(""))
                logOut();

            }
        });

        return view;

    }

    public ArrayList<Picture> buildPictures(){
        //Metodo que genera array de pioctures desde internet
        ArrayList<Picture> pictures = new ArrayList<>();
        pictures.add(new Picture("http://www.novalandtours.com/images/guide/guilin.jpg","Martin Gomez","4 dias","4 Me gusta"));
        pictures.add(new Picture("https://www.definicionabc.com/wp-content/uploads/Paisaje-Natural.jpg","Pompeya Gomez","2 dias","5 Me gusta"));
        pictures.add(new Picture("https://quelindoesmipaisaje.files.wordpress.com/2011/02/paisaje.jpg","Mariana Ceballos","1 dias","2 Me gusta"));

        return pictures;
    }

    public void showToolbar(String title,boolean upButton, View view ) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(title);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(upButton); // Habilita boton Back
    }



    public void logOut() {
        Log.w("Logout", "Logout test" );

       // Toast.makeText( this,  "Logout", Toast.LENGTH_SHORT).show();
        firebaseAuth = FirebaseAuth.getInstance(); // Obtiene la informacion desde json

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null){ //Valida si esta logueado
            Log.w("Logout", "Usuario Logueado" + firebaseUser.getEmail());
            firebaseAuth.signOut();
            // goHome();//Si esta logueado no va a login
        }
        else{
            Log.w("Logout", "Usuario No Logueado" );
        }

        //LoginManager.getInstance().logOut();
    }



}
