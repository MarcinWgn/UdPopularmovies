package com.wegrzyn.marcin.popularmoviesst1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

/**
 * Created by Marcin Węgrzyn on 24.02.2018.
 * wireamg@gmail.com
 */

class MoviesLoader extends AsyncTaskLoader<List<Movie>> {

    private final int queryType;

    MoviesLoader(@NonNull Context context, int queryType) {
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

        if(queryType == NetworkUtils.REQUEST_TOP){
            return  NetworkUtils.queryMovies(NetworkUtils.TOP_RATED_QUERY);
        }else if (queryType == NetworkUtils.REQUEST_POPULAR){
            return NetworkUtils.queryMovies(NetworkUtils.POPULAR_QUERY);
        }else {
        return BaseUtils.queryDataBase(getContext());
        }

    }
}
