package com.wegrzyn.marcin.popularmoviesst1;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.wegrzyn.marcin.popularmoviesst1.NetworkUtils.TARGET_HEIGHT;
import static com.wegrzyn.marcin.popularmoviesst1.NetworkUtils.TARGET_WIDTH;

public class DetailActivity extends AppCompatActivity {

    private final static int TRAILER_LOADER_ID = 2;
    private final static int REVIEW_LOADER_ID = 3;

    private String idMovie;
    private List<Trailer> trailerList = new ArrayList<>();
    private TrailersAdapter trailersAdapter;

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

            idMovie = movie.getId();
            Log.d(NetworkUtils.TAG,"id= "+ idMovie);
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

        initLoaders();
        RecyclerView recyclerView = findViewById(R.id.trailer_recyclerview);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        trailersAdapter = new TrailersAdapter(trailerList,this);
        recyclerView.setAdapter(trailersAdapter);
}


private void initLoaders() {
        getSupportLoaderManager().initLoader(TRAILER_LOADER_ID, null, new LoaderManager.LoaderCallbacks<List<Trailer>>() {
            @Override
            public Loader<List<Trailer>> onCreateLoader(int id, Bundle args) {
                Log.d(NetworkUtils.TAG,"onCreateTrailersLoader");
                return new TrailersLoader(getApplication(), idMovie);
            }

            @Override
            public void onLoadFinished(Loader<List<Trailer>> loader, List<Trailer> data) {

                trailersAdapter.setData(data);
                trailersAdapter.notifyDataSetChanged();
                Log.d(NetworkUtils.TAG,"TRAILERS#######################");
                for (Trailer trailer:trailerList) {
                    Log.d(NetworkUtils.TAG,"name: "+trailer.getName());
                    Log.d(NetworkUtils.TAG,"key: "+trailer.getKey());
                }
            }

            @Override
            public void onLoaderReset(Loader<List<Trailer>> loader) {

            }
        });
        getSupportLoaderManager().initLoader(REVIEW_LOADER_ID, null, new LoaderManager.LoaderCallbacks<List<Review>>() {
            @Override
            public Loader<List<Review>> onCreateLoader(int id, Bundle args) {
                Log.d(NetworkUtils.TAG,"onCreateReviewsLoader");
                return new ReviewsLoader(getApplication(),idMovie);
            }

            @Override
            public void onLoadFinished(Loader<List<Review>> loader, List<Review> data) {


                Log.d(NetworkUtils.TAG,"REVIEWS#######################");
                for (Review review:data) {
                    Log.d(NetworkUtils.TAG,"author: "+review.getAuthor());
                    Log.d(NetworkUtils.TAG,"content: "+review.getContent());
                }
            }
            @Override
            public void onLoaderReset(Loader<List<Review>> loader) {

            }
        });
    }
}

