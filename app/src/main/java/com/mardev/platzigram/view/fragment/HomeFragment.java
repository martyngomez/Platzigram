package com.mardev.platzigram.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mardev.platzigram.R;
import com.mardev.platzigram.adapter.PictureAdapterRecyclerView;
import com.mardev.platzigram.model.Picture;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);

        showToolbar("Home",false,view);
        RecyclerView picturesRecycler = (RecyclerView)view.findViewById(R.id.pictureRecycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext()); //Layout Manager
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        picturesRecycler.setLayoutManager(linearLayoutManager);

        PictureAdapterRecyclerView pictureAdapterRecyclerView = new PictureAdapterRecyclerView(buildPictures(),R.layout.cardview_picure, getActivity());

        picturesRecycler.setAdapter(pictureAdapterRecyclerView);
        return view;

    }
    public ArrayList<Picture> buildPictures(){
        //Metodo que genera array de pioctures desde internet
        ArrayList<Picture> pictures = new ArrayList<>();
        pictures.add(new Picture("http://www.novalandtours.com/images/guide/guilin.jpg","Martin Gomez","4 dias","4 "));
        pictures.add(new Picture("https://www.definicionabc.com/wp-content/uploads/Paisaje-Natural.jpg","Pompeya Gomez","2 dias","5 "));
        pictures.add(new Picture("https://quelindoesmipaisaje.files.wordpress.com/2011/02/paisaje.jpg","Mariana Ceballos","1 dias","2 "));

        return pictures;
    }

    public void showToolbar(String title,boolean upButton, View view ) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(title);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(upButton); // Habilita boton Back
    }

}