 package com.martyngomez.platzigram.post.view;

 import android.graphics.Color;
 import android.net.Uri;
 import android.os.Build;
 import android.os.Bundle;
 import android.support.annotation.NonNull;
 import android.support.design.widget.CollapsingToolbarLayout;
 import android.support.v7.app.AppCompatActivity;
 import android.support.v7.widget.Toolbar;
 import android.transition.Fade;
 import android.widget.ImageView;
 import android.widget.Toast;

 import com.google.android.gms.tasks.OnFailureListener;
 import com.google.android.gms.tasks.OnSuccessListener;
 import com.google.firebase.crash.FirebaseCrash;
 import com.google.firebase.storage.StorageReference;
 import com.martyngomez.platzigram.PlatzigramApplication;
 import com.martyngomez.platzigram.R;
 import com.squareup.picasso.Picasso;

 public class PictureDetailActivity extends AppCompatActivity {
     private static final String PHOTO_NAME = "JPEG_20180119_10-39-56_-753014557.jpg" ;
     private static final String TAG = "PictureDetailActivity";
     private ImageView imageeader;
     private PlatzigramApplication app;
     StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseCrash.log("Inicializando " + TAG);
        setContentView(R.layout.activity_picture_detail);

        app = (PlatzigramApplication) getApplicationContext();
        storageReference = app.getStorageReference(); //Obtiene la URL

        imageeader = (ImageView) findViewById(R.id.imageHeader);

        showToolbar("", true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setStatusBarColor(Color.TRANSPARENT); // Para que saque la barra superior , solo valido para lollipop en adelante
            getWindow().setEnterTransition(new Fade());
        }

        showData();

    }

     private void showData() {
        storageReference.child("postImages/" + PHOTO_NAME).getDownloadUrl() //Accede a URL
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(PictureDetailActivity.this).load(uri.toString()).into(imageeader);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PictureDetailActivity. this, "Error al traer foto", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                FirebaseCrash.report(e);
            }
        });
     }

     public void showToolbar(String title,boolean upButton ) {
         Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
         setSupportActionBar(toolbar);
         getSupportActionBar().setTitle(title);
         getSupportActionBar().setDisplayHomeAsUpEnabled(upButton); // Habilita boton Back
         CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);
     }
}
