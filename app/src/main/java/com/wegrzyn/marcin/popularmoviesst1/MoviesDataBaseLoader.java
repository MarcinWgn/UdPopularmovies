package com.wegrzyn.marcin.popularmoviesst1;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.wegrzyn.marcin.popularmoviesst1.data.MovieContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marcin Węgrzyn on 13.03.2018.
 * wireamg@gmail.com
 */

public class MoviesDataBaseLoader extends AsyncTaskLoader<List<Movie>> {

    private final String [] selectedColumn ={
            MovieContract.MovieEntry.ID_MOVIE,
            MovieContract.MovieEntry.TITLE,
            MovieContract.MovieEntry.RELASE_DATE,
            MovieContract.MovieEntry.POSTER_LOCAL,
            MovieContract.MovieEntry.VOTE_AVERAGE,
            MovieContract.MovieEntry.PLOT_SYNOPSIS
    };
    private List<Movie> movieList = new ArrayList<>();

    MoviesDataBaseLoader(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<Movie> loadInBackground() {

        Cursor cursor = getContext().getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI,selectedColumn,
                null,null,null);

        if (cursor != null && cursor.moveToFirst()) {
            movieList.clear();
            do {
                String idMovie = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.ID_MOVIE));
                String title = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.TITLE));
                String releaseDate = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.RELASE_DATE));
                String posterLocal = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.POSTER_LOCAL));
                String voteAverage = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.VOTE_AVERAGE));
                String plotSynopsis = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.PLOT_SYNOPSIS));

                movieList.add(new Movie(idMovie, title, releaseDate, posterLocal, voteAverage, plotSynopsis));

            } while (cursor.moveToNext());
            cursor.close();
        }
        return movieList;
    }
}
