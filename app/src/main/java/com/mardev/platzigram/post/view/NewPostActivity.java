package com.mardev.platzigram.post.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.mardev.platzigram.R;
import com.squareup.picasso.Picasso;

public class NewPostActivity extends AppCompatActivity {
    private ImageView imgPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        imgPhoto = (ImageView) findViewById(R.id.imgPhoto);

        if (getIntent().getExtras()!= null){
            String photoPath = getIntent().getExtras().getString("PHOTO_PATH_TEMP"); //Obtiene parametro
            Picasso.with(this).load(photoPath).into(imgPhoto); //Carga la foto
        }
    }
}
