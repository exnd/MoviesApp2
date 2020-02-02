package com.example.favoriteapp;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class DetailMovie extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        Movie selectMovie = getIntent().getParcelableExtra(EXTRA_MOVIE);

        if(selectMovie!=null){
            ImageView imgFoto = findViewById(R.id.image_movie);

            Glide.with(this)
                    .load(selectMovie.getPhoto())
                    .into(imgFoto);


            TextView txtNama = findViewById(R.id.text_name);
            txtNama.setText(selectMovie.getName());

            TextView txtScore = findViewById(R.id.text_desc);
            txtScore.setText(selectMovie.getDescription());


        }


    }


}
