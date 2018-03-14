package com.wegrzyn.marcin.popularmoviesst1;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.wegrzyn.marcin.popularmoviesst1.data.MoviesDbHelper;

import java.util.ArrayList;
import java.util.List;

import static com.wegrzyn.marcin.popularmoviesst1.NetworkUtils.REQUEST_FAVORITE;
import static com.wegrzyn.marcin.popularmoviesst1.NetworkUtils.REQUEST_POPULAR;
import static com.wegrzyn.marcin.popularmoviesst1.NetworkUtils.REQUEST_TOP;
import static com.wegrzyn.marcin.popularmoviesst1.NetworkUtils.isInternetConnections;
import static com.wegrzyn.marcin.popularmoviesst1.data.MovieContract.*;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.ListItemClickListener,
        LoaderManager.LoaderCallbacks<List<Movie>> {

    private static final String TAG = MainActivity.class.getSimpleName();

    public static final String ITEM_MOVIE = "item_movie";

    private static final int MOVIES_LOADER_ID = 30;
    private static final int MOVIES_BASE_LOADER = 40;

    public static final String REQ_CATEGORY = "req_category";
    private List<Movie> moviesList = new ArrayList<>(20);

    private MoviesAdapter adapter;
    private ProgressBar progressBar;
    
    private int requestCategory = REQUEST_TOP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            requestCategory = savedInstanceState.getInt(REQ_CATEGORY);
        }

        progressBar = findViewById(R.id.progress);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);


        RecyclerView.LayoutManager mLayoutManager =
                new GridLayoutManager(this, getResources().getInteger(R.integer.column_size));
        recyclerView.setLayoutManager(mLayoutManager);

        adapter = new MoviesAdapter(this, moviesList, this);
        recyclerView.setAdapter(adapter);

            if(requestCategory==REQUEST_FAVORITE){
                getSupportLoaderManager().initLoader(MOVIES_BASE_LOADER,null, this);
            } else if(requestCategory==REQUEST_TOP||requestCategory==REQUEST_POPULAR) {
                getSupportLoaderManager().initLoader(MOVIES_LOADER_ID,null, this);
            }

        internetToast();

        setActionBarText();

    }

    private void internetToast() {
        if(!isInternetConnections(this))
            Toast.makeText(this,getText(R.string.no_internet),Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"Request category: "+String.valueOf(requestCategory));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.popular_menu_id:
                selectPopular();
                return true;
            case R.id.top_rated_menu_id:
                selectTop();
                return true;
            case R.id.favorite_menu_id:
                selectFavorite();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void selectTop() {
        requestCategory = REQUEST_TOP;
            getSupportLoaderManager().restartLoader(MOVIES_LOADER_ID, null, this);
        setActionBarText();
    }

    private void selectPopular() {
        requestCategory = REQUEST_POPULAR;
            getSupportLoaderManager().restartLoader(MOVIES_LOADER_ID, null, this);
            setActionBarText();
    }
    private void selectFavorite(){
        requestCategory = REQUEST_FAVORITE;
        if (getSupportActionBar() != null){
            getSupportLoaderManager().restartLoader(MOVIES_BASE_LOADER,null,this);
            setActionBarText();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(REQ_CATEGORY, requestCategory);
    }

    @Override
    public void onListItemClick(int clickItemIndex) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(ITEM_MOVIE,moviesList.get(clickItemIndex));
        startActivity(intent);
    }


    @NonNull
    @Override
    public Loader<List<Movie>> onCreateLoader(int id, Bundle args) {

        Log.d(TAG, "onCreateLoader");
        progressBar.setVisibility(View.VISIBLE);
        internetToast();
        switch (id) {
            case MOVIES_LOADER_ID:
                if (requestCategory == REQUEST_POPULAR) {
                    Log.d(TAG, "onCreateLoader popular");
                    return new MoviesLoader(this, NetworkUtils.POPULAR_QUERY);
                } else if (requestCategory == REQUEST_TOP) {
                    Log.d(TAG, "onCreateLoader top");
                    return new MoviesLoader(this, NetworkUtils.TOP_RATED_QUERY);
                }
                break;
            case MOVIES_BASE_LOADER:
                if (requestCategory == REQUEST_FAVORITE) {
                    Log.d(TAG, "onCreateLoader favorite");
                    return new MoviesDataBaseLoader(this);
                }
                break;
        }
        return new MoviesDataBaseLoader(this);
    }

    private void setActionBarText() {
        if (requestCategory==REQUEST_POPULAR) {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(getResources().getString(R.string.popular));
            }
        }else if(requestCategory==REQUEST_TOP) {
            if (getSupportActionBar() != null){
                getSupportActionBar().setTitle(getResources().getString(R.string.top_rated));
            }
        }else if(requestCategory==REQUEST_FAVORITE){
            if (getSupportActionBar() != null){
                getSupportActionBar().setTitle(R.string.favorite);
            }
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Movie>> loader, List<Movie> data) {

        moviesList = data;
        adapter.setData(moviesList);
        adapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
        Log.d(TAG,"onLoadFinished"+loader.getId());
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Movie>> loader) {
        Log.d(TAG,"reset loader");
    }

}
