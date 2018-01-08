package com.mardev.platzigram;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mardev.platzigram.view.ContainerActivity;
import com.mardev.platzigram.view.CreateAccountActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Otra forma de hacer un intent implicito
       /* ImageView logo = (ImageView) findViewById(R.id.logo);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://platzigram.com/"));
                startActivity(intent);
            }
        });*/
    }

    public void goCreateAccount(View view){
        Intent intent = new Intent(this, CreateAccountActivity.class); //Prmer parametro :actividad origen, seundo actividad destino
        startActivity(intent);
    }

    public void goLogin(View view){
        Intent intent = new Intent(this, ContainerActivity.class); //Prmer parametro :actividad origen, seundo actividad destino
        startActivity(intent);
    }

    //Deesafio 2
    public void goWeb(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://www.google.com"));
        startActivity(intent);
    }





}
