package com.wegrzyn.marcin.popularmoviesst1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marcin Węgrzyn on 24.02.2018.
 * wireamg@gmail.com
 */

public class MoviesLoader extends AsyncTaskLoader<List<Movie>> {

    private String queryType;

    MoviesLoader(@NonNull Context context, String queryType) {
        super(context);
        this.queryType = queryType;
    }

    @Nullable
    @Override
    public List<Movie> loadInBackground() {

        Log.d(NetworkUtils.TAG,"loadInBackground");
        if(queryType==null)return null;

        String jsonString = NetworkUtils.queryUrl(queryType);


        return NetworkUtils.parseResultsJson(jsonString);
    }
}
