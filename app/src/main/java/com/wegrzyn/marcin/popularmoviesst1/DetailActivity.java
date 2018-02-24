package com.wegrzyn.marcin.popularmoviesst1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static com.wegrzyn.marcin.popularmoviesst1.NetworkUtils.TARGET_HEIGHT;
import static com.wegrzyn.marcin.popularmoviesst1.NetworkUtils.TARGET_WIDTH;

public class DetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView title = findViewById(R.id.detail_title_TV);
        TextView synopsis = findViewById(R.id.detail_plot_synopsis_TV);
        TextView rating = findViewById(R.id.detail_rating_TV);
        TextView date = findViewById(R.id.detail_release_date_TV);

        ImageView poster = findViewById(R.id.detail_poster_IW);


        if (getIntent().hasExtra(MainActivity.ITEM_MOVIE)) {
            Movie movie = getIntent().getParcelableExtra(MainActivity.ITEM_MOVIE);

            title.setText(movie.getTitle());
            synopsis.setText(movie.getPlotSynopsis());
            rating.setText(movie.getVoteAverage());
            String[] releaseDate = movie.getReleaseDate().split("-");
            date.setText(releaseDate[0]);

            Log.d(NetworkUtils.TAG, movie.getPosterLocalization());
            Picasso.with(this)
                    .load(NetworkUtils.getImageUri(movie.getPosterLocalization()))
                    .resize(TARGET_WIDTH, TARGET_HEIGHT)
                    .into(poster);
        }
    }
}

