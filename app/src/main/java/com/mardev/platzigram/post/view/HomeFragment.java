package com.mardev.platzigram.post.view;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
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

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private FloatingActionButton fabCamera;
    private static final int REQUEST_CAMERA= 1;
    private String photoPathTemp = "";


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

        Intent intentTakePicture = new Intent (MediaStore.ACTION_IMAGE_CAPTURE); // Intent implicito // Abre camara
        if(intentTakePicture.resolveActivity(getActivity().getPackageManager())!= null)  { //Valida la existencia de una camara
            File photoFile = null ;

            try {
                photoFile = createImageFile();
            }catch(Exception e)
            {
                e.printStackTrace();
            }

            if (photoFile != null){
              //  String packageName = getActivity().getApplicationContext().getPackageName(); //Obetern package
                Uri photoUri = FileProvider.getUriForFile(getActivity(),"com.mardev.platzigram", photoFile ); //Obtiene Uri de la foto
                intentTakePicture.putExtra(MediaStore.EXTRA_OUTPUT,photoUri); //Almacienamiento para la foto
                startActivityForResult(intentTakePicture, REQUEST_CAMERA); //Abre camara

            }


        }
    }

    private File createImageFile() throws IOException {
        String timeStamp= new SimpleDateFormat("yyyyMMdd_HH-mm-ss").format(new Date()); // Obtiene la hora
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir= getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES) ; //Obtiene ruta default de imagenes //El objeto File se usa tambien para directorios
        File photo = File.createTempFile(imageFileName,".jpg",storageDir); //archivo / extensión / ruta destino

        photoPathTemp = "file:" + photo.getAbsolutePath(); //Ruta absoluta de la foto temporal

        return photo;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) { //Obtiene el resultado de la camara // callback
       if( requestCode == REQUEST_CAMERA && resultCode == getActivity().RESULT_OK ){
           Log.d("HomeFragment","Camera OK ! :)");
           Intent i = new Intent(getActivity(), NewPostActivity.class);
           i.putExtra("PHOTO_PATH_TEMP",photoPathTemp);
           startActivity(i);
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
