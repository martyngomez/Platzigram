package com.mardev.platzigram.post.view;


import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

    private FloatingActionButton fabCamera;
    private static final int REQUEST_CAMERA= 1;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);

        showToolbar(getResources().getString(R.string.tab_home),false,view);
        RecyclerView picturesRecycler = (RecyclerView)view.findViewById(R.id.pictureRecycler);

        fabCamera= (FloatingActionButton) view.findViewById(R.id.fabCamera);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext()); //Layout Manager
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        picturesRecycler.setLayoutManager(linearLayoutManager);

        PictureAdapterRecyclerView pictureAdapterRecyclerView = new PictureAdapterRecyclerView(buildPictures(),R.layout.cardview_picure, getActivity());

        picturesRecycler.setAdapter(pictureAdapterRecyclerView);


        fabCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture();
            }
        });




        return view;

    }

    private void takePicture() {

        Intent intentTakePicture = new Intent (MediaStore.ACTION_IMAGE_CAPTURE); // Intent implicito
        if(intentTakePicture.resolveActivity(getActivity().getPackageManager())!= null)  { //Valida la existencia de una camara
            startActivityForResult(intentTakePicture, REQUEST_CAMERA); //Abre camara
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) { //Obtiene el resultado de la camara
       if( requestCode == REQUEST_CAMERA && resultCode == getActivity().RESULT_OK ){
           Log.d("HomeFragment","Camera OK ! :)");
        }
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

}
