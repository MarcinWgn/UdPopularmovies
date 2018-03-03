package com.wegrzyn.marcin.popularmoviesst1;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by Marcin Węgrzyn on 03.03.2018.
 * wireamg@gmail.com
 */

public class ReviewsLoader extends android.support.v4.content.AsyncTaskLoader<List<Review>> {

    private final String id;

    ReviewsLoader(Context context, String id) {
        super(context);
        this.id = id;
    }

    @Override
    protected void onStartLoading() { forceLoad(); }

    @Override
    public List<Review> loadInBackground() {
        if(id==null)return null;
        return NetworkUtils.queryReview(id);
    }
}
