package com.martyngomez.platzigram.post.view;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.martyngomez.platzigram.PlatzigramApplication;
import com.martyngomez.platzigram.R;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class NewPostActivity extends AppCompatActivity {
    private static final String TAG ="NewPostActivity" ;
    private ImageView imgPhoto;
    private Button btnCreatePost;
    private String photoPath;
    private PlatzigramApplication app;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        app = (PlatzigramApplication) getApplicationContext(); // inicializa app para traer datos globales
        storageReference =  app.getStorageReference();

        imgPhoto = (ImageView) findViewById(R.id.imgPhoto);
        btnCreatePost = (Button) findViewById(R.id.btnCreatePost);

        if (getIntent().getExtras()!= null){
            photoPath = getIntent().getExtras().getString("PHOTO_PATH_TEMP"); //Obtiene parametro
            showPhoto();
        }

        btnCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadPhoto();
            }
        });
    }

    private void uploadPhoto() {
        imgPhoto.setDrawingCacheEnabled(true);
        imgPhoto.buildDrawingCache(); //Genera un cache de la foto

        Bitmap bitmap = imgPhoto.getDrawingCache(); //Pasa la foto del cache a un Bitmap
        ByteArrayOutputStream baos =  new ByteArrayOutputStream(); //Se utiliza para la escritura en Firebase

        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos); // Comprime la foto

        byte[] photoByte = baos.toByteArray(); // Asigna foto a bytes

        String photoName = photoPath.substring(photoPath.lastIndexOf("/")+1,photoPath.length()); //Corta en nombre de la foto

        StorageReference photoReference =  storageReference.child("postImages/" + photoName);

        UploadTask uploadTask = photoReference.putBytes(photoByte); // sube la imagen , putfile es para trabajar con archivos
        uploadTask.addOnFailureListener(new OnFailureListener() { //Listener fallo
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() { //istner exito
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri uriPhoto = taskSnapshot.getDownloadUrl();
                String photoURL = uriPhoto.toString();
                Log.w(TAG, "URL Photo = " + photoURL);
                finish();
            }
        });





    }

    private void showPhoto() {
        Picasso.with(this).load(photoPath).into(imgPhoto); //Carga la foto
    }
}
