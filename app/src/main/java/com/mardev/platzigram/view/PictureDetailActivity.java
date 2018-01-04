 package com.mardev.platzigram.view;

 import android.os.Bundle;
 import android.support.design.widget.CollapsingToolbarLayout;
 import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mardev.platzigram.R;

 public class PictureDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_detail);
        showToolbar("", true);
    }

     public void showToolbar(String title,boolean upButton ) {
         Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
         setSupportActionBar(toolbar);
         getSupportActionBar().setTitle(title);
         getSupportActionBar().setDisplayHomeAsUpEnabled(upButton); // Habilita boton Back
         CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);
     }
}
