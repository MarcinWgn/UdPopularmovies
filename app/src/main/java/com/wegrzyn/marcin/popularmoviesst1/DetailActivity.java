package com.wegrzyn.marcin.popularmoviesst1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wegrzyn.marcin.popularmoviesst1.data.MovieContract;
import com.wegrzyn.marcin.popularmoviesst1.data.MoviesDbHelper;

import java.util.ArrayList;
import java.util.List;

import static com.wegrzyn.marcin.popularmoviesst1.NetworkUtils.TARGET_HEIGHT;
import static com.wegrzyn.marcin.popularmoviesst1.NetworkUtils.TARGET_WIDTH;
import static com.wegrzyn.marcin.popularmoviesst1.NetworkUtils.isInternetConnections;

public class DetailActivity extends AppCompatActivity implements TrailersAdapter.ListItemClickListener {

    private final static int TRAILER_LOADER_ID = 40;
    private final static int REVIEW_LOADER_ID = 50;

    private String idMovie;
    private List<Trailer> trailerList = new ArrayList<>();
    private List<Review> reviewList = new ArrayList<>();
    private TrailersAdapter trailersAdapter;
    private ReviewsAdapter reviewsAdapter;
    private ProgressBar trailerProgressBar;
    private ProgressBar reviewProgressBar;
    private TextView trailerLabel;
    private TextView reviewLabel;
    private ImageButton favImageButton;
    private Movie movie;

    private boolean isFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final TextView title = findViewById(R.id.detail_title_TV);
        TextView synopsis = findViewById(R.id.detail_plot_synopsis_TV);
        TextView rating = findViewById(R.id.detail_rating_TV);
        TextView date = findViewById(R.id.detail_release_date_TV);
        trailerProgressBar = findViewById(R.id.trailer_pb);
        reviewProgressBar = findViewById(R.id.review_pb);
        trailerLabel = findViewById(R.id.trailer_label_tv);
        reviewLabel = findViewById(R.id.review_label_tv);
        favImageButton = findViewById(R.id.favorite_ib);

        ImageView poster = findViewById(R.id.detail_poster_IW);




        if (getIntent().hasExtra(MainActivity.ITEM_MOVIE)) {
            movie = getIntent().getParcelableExtra(MainActivity.ITEM_MOVIE);

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

       if(isInternetConnections(this)) initLoaders();

        RecyclerView trailersRecyclerView = findViewById(R.id.trailer_recyclerview);
        trailersRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager trailersLayoutManager =
                new LinearLayoutManager(this);
        trailersRecyclerView.setLayoutManager(trailersLayoutManager);

        RecyclerView reviewsRecyclerView = findViewById(R.id.review_recyclerview);
        reviewsRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager reviewsLayoutManager =
                new LinearLayoutManager(this);
        reviewsRecyclerView.setLayoutManager(reviewsLayoutManager);


        trailersAdapter = new TrailersAdapter(trailerList,this, this);
        trailersRecyclerView.setAdapter(trailersAdapter);

        reviewsAdapter = new ReviewsAdapter(reviewList,this);
        reviewsRecyclerView.setAdapter(reviewsAdapter);

        favImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFavorite();
                checkFavorite();
            }
        });

        checkFavorite();
}

private void setFavorite(){
        if(isFavorite){
            Uri uriDbMovie = MovieContract.getUriMovie(movie.getId());
            int i =getContentResolver().delete(uriDbMovie,null,null);
            Log.d(NetworkUtils.TAG, "skasowane: "+String.valueOf(i));
        }else{
            ContentValues contentValues = movie.getContentValues();
            Uri answ = getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI,contentValues);
            Log.d(NetworkUtils.TAG, "dodano do bazy: "+answ);
        }
}

private void checkFavorite(){

     final String [] selectedColumn ={
            MovieContract.MovieEntry.ID_MOVIE
    };
    Uri uriDbMovie = MovieContract.getUriMovie(movie.getId());
    Cursor cursor = getBaseContext().getContentResolver().query(uriDbMovie,selectedColumn,null,null,null);
    Drawable drawable;
    if(cursor!=null && cursor.getCount()>0){
        drawable = ContextCompat.getDrawable(this,android.R.drawable.btn_star_big_on);
        isFavorite = true;
    }else {
        drawable =  ContextCompat.getDrawable(this,android.R.drawable.btn_star_big_off);
        isFavorite = false;
    }
    if (cursor != null) {
        cursor.close();
    }
    favImageButton.setBackground(drawable);
}

private void initLoaders() {
        getSupportLoaderManager().initLoader(TRAILER_LOADER_ID, null, new LoaderManager.LoaderCallbacks<List<Trailer>>() {
            @NonNull
            @Override
            public Loader<List<Trailer>> onCreateLoader(int id, Bundle args) {
                trailerProgressBar.setVisibility(View.VISIBLE);
                return new TrailersLoader(getApplication(), idMovie);
            }

            @Override
            public void onLoadFinished(@NonNull Loader<List<Trailer>> loader, List<Trailer> data) {
                trailerList=data;
                trailersAdapter.setData(data);
                trailersAdapter.notifyDataSetChanged();
                trailerProgressBar.setVisibility(View.INVISIBLE);
                if(data==null||data.isEmpty())trailerLabel.setVisibility(View.GONE);
            }

            @Override
            public void onLoaderReset(@NonNull Loader<List<Trailer>> loader) {

            }
        });
        getSupportLoaderManager().initLoader(REVIEW_LOADER_ID, null, new LoaderManager.LoaderCallbacks<List<Review>>() {
            @NonNull
            @Override
            public Loader<List<Review>> onCreateLoader(int id, Bundle args) {
                reviewProgressBar.setVisibility(View.VISIBLE);
                return new ReviewsLoader(getApplication(),idMovie);
            }

            @Override
            public void onLoadFinished(@NonNull Loader<List<Review>> loader, List<Review> data) {
                reviewList=data;
                reviewsAdapter.setData(data);
                reviewsAdapter.notifyDataSetChanged();
                reviewProgressBar.setVisibility(View.INVISIBLE);
                if(data==null||data.isEmpty())reviewLabel.setVisibility(View.GONE);
            }
            @Override
            public void onLoaderReset(@NonNull Loader<List<Review>> loader) {

            }
        });
    }

    @Override
    public void onListItemClick(int clickItem) {

        trailerIntent(trailerList.get(clickItem).getKey());
    }
    private void trailerIntent(String key){
        Intent intent = new Intent(Intent.ACTION_VIEW,NetworkUtils.getYtUri(key));
        if(intent.resolveActivity(getPackageManager())!=null)
        startActivity(intent);
    }
}

