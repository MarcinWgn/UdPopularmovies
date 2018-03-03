package com.wegrzyn.marcin.popularmoviesst1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

/**
 * Created by Marcin Węgrzyn on 03.03.2018.
 * wireamg@gmail.com
 */

public class TrailersLoader extends AsyncTaskLoader<List<Trailer>> {

    private final String id;

    TrailersLoader(@NonNull Context context, String id) {
        super(context);
        this.id = id;
    }

    @Override
    protected void onStartLoading() { forceLoad();}

    @Nullable
    @Override
    public List<Trailer> loadInBackground() {

        if(id==null)return null;
        return NetworkUtils.gueryTrailer(id);
    }
}
