package com.wegrzyn.marcin.popularmoviesst1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Marcin Węgrzyn on 24.02.2018.
 * wireamg@gmail.com
 */

class MoviesLoader extends AsyncTaskLoader<List<Movie>> {

    private final String queryType;

    MoviesLoader(@NonNull Context context, String queryType) {
        super(context);
        this.queryType = queryType;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<Movie> loadInBackground() {

        if (queryType == null) return null;
        return NetworkUtils.queryMovies(queryType);
    }
}
