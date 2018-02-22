package com.wegrzyn.marcin.popularmoviesst1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if(getIntent().hasExtra(Intent.EXTRA_TEXT)){
          int i = getIntent().getIntExtra(Intent.EXTRA_TEXT,0);

            Toast.makeText(this,NetworkUtils.moviesList.get(i).getTitle(),Toast.LENGTH_LONG).show();
        }


    }
}
