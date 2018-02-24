package com.wegrzyn.marcin.popularmoviesst1;

import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.ListItemClickListener,
        LoaderManager.LoaderCallbacks<List<Movie>> {

    public static final String ITEM_MOVIE = "item_movie";
    private static final String POPULAR_STATE = "popular_state";

    private static final int MoviesLoaderID = 3;
    private List<Movie> moviesList = new ArrayList<>(20);

    private MoviesAdapter adapter;
    private ProgressBar progressBar;

    private boolean popular;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            popular = savedInstanceState.getBoolean(POPULAR_STATE);
        }



        progressBar = findViewById(R.id.progress);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);


        RecyclerView.LayoutManager mLayoutManager =
                new GridLayoutManager(this, getResources().getInteger(R.integer.column_size));
        recyclerView.setLayoutManager(mLayoutManager);

        adapter = new MoviesAdapter(this, new ArrayList<Movie>(), this);
        recyclerView.setAdapter(adapter);

        adapter = new MoviesAdapter(this, moviesList, this);
        recyclerView.setAdapter(adapter);

        getSupportLoaderManager().initLoader(MoviesLoaderID, null, this).forceLoad();

        setActionBarText();
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
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void selectTop() {
        popular = false;
        getSupportLoaderManager().restartLoader(MoviesLoaderID, null, this).forceLoad();
        setActionBarText();
    }

    private void selectPopular() {
        popular = true;
        getSupportLoaderManager().restartLoader(MoviesLoaderID, null, this).forceLoad();
        setActionBarText();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(POPULAR_STATE, popular);
    }

    @Override
    public void onListItemClick(int clickItemIndex) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(ITEM_MOVIE,moviesList.get(clickItemIndex));
        startActivity(intent);
    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int id, Bundle args) {

        progressBar.setVisibility(View.VISIBLE);
        if (popular) {
            return new MoviesLoader(this, NetworkUtils.POPULAR_QUERY);
        } else {
            return new MoviesLoader(this, NetworkUtils.TOP_RATED_QUERY);
        }
    }

    private void setActionBarText() {
        if (popular) {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(getResources().getString(R.string.popular));
            }
        }else {
            if (getSupportActionBar() != null){
                getSupportActionBar().setTitle(getResources().getString(R.string.top_rated));
            }
        }
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> data) {
        moviesList = data;
        adapter.setData(moviesList);
        adapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {

    }

}
