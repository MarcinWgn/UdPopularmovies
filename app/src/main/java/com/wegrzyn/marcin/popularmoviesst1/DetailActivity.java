package com.wegrzyn.marcin.popularmoviesst1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView title = findViewById(R.id.detail_title_TV);
        TextView synopsis = findViewById(R.id.detail_plot_synopsis_TV);
        TextView rating = findViewById(R.id.detail_rating_TV);
        TextView date = findViewById(R.id.detail_relase_date_TV);

        ImageView poster = findViewById(R.id.detail_poster_IW);


        if(getIntent().hasExtra(MainActivity.ITEM_INDEX)){
          int i = getIntent().getIntExtra(MainActivity.ITEM_INDEX,0);

          Movie movie = NetworkUtils.moviesList.get(i);

          title.setText(movie.getTitle());
          synopsis.setText(movie.getPlotSynopsis());
          rating.setText(movie.getVoteAverage());
          String[] relaseDate = movie.getReleaseDate().split("-");
          date.setText(relaseDate[0]);

            Log.d(NetworkUtils.TAG,movie.getPosterLocalization());
            Picasso.with(this)
                    .load(NetworkUtils.getImageUri(movie.getPosterLocalization()))
                    .resize(400,600)
                    .into(poster);
        }
    }
}
