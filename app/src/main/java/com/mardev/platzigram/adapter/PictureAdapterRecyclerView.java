package com.mardev.platzigram.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mardev.platzigram.R;
import com.mardev.platzigram.model.Picture;

import java.util.ArrayList;

/**
 * Created by MTN on 28/12/2017.
 */

public class PictureAdapterRecyclerView extends RecyclerView.Adapter<PictureAdapterRecyclerView.PictureViewHolder> {

    private ArrayList<Picture> pictures;
    private int resource; //Cardview
    private Activity activity; // Actividad desde donde se esta llamando

    public PictureAdapterRecyclerView(ArrayList<Picture> picture, int resource, Activity activity) {
        this.pictures = picture;
        this.resource = resource;
        this.activity = activity;
    }

    @Override
    public PictureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent,false); // asigna layout a variable java
        return new PictureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PictureViewHolder holder, int position) { // Trabaja con la lista de elementos
        //Metodo que Recorre la lista y va agrerando tarjetas con datos
        Picture picture = pictures.get(position);
        holder.usernameCard.setText(picture.getUsername());
        holder.timeCard.setText(picture.getTime());
        holder.likeNumberCard.setText(picture.getLike_number());

    }

    @Override
    public int getItemCount() {
        //Metodo que devuelve el conteo de cuantos elementos hay
        return pictures.size();
    }

    public class PictureViewHolder extends RecyclerView.ViewHolder{ //Clase viewHolder
        private ImageView pictureCard;
        private TextView usernameCard;
        private TextView timeCard;
        private TextView likeNumberCard;

        public PictureViewHolder(View itemView) {
            super(itemView);

            pictureCard = (ImageView) itemView.findViewById(R.id.pictureCard);
            usernameCard = (TextView) itemView.findViewById(R.id.userNameCard);
            timeCard = (TextView) itemView.findViewById(R.id.timeCard);
            likeNumberCard = (TextView) itemView.findViewById(R.id.likeNumberCard);
        }
    }
}
