package com.wegrzyn.marcin.popularmoviesst1;

import android.content.Context;
import android.database.Cursor;

import com.wegrzyn.marcin.popularmoviesst1.data.MovieContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marcin Węgrzyn on 14.03.2018.
 * wireamg@gmail.com
 */

  class BaseUtils {

    private static final String [] selectedColumn ={
            MovieContract.MovieEntry.ID_MOVIE,
            MovieContract.MovieEntry.TITLE,
            MovieContract.MovieEntry.RELEASE_DATE,
            MovieContract.MovieEntry.POSTER_LOCAL,
            MovieContract.MovieEntry.VOTE_AVERAGE,
            MovieContract.MovieEntry.PLOT_SYNOPSIS
    };


    static List<Movie> queryDataBase(Context context){
        Cursor cursor = context.getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI,selectedColumn,
                null,null,null);

        List<Movie> movieList = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String idMovie = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.ID_MOVIE));
                String title = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.TITLE));
                String releaseDate = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.RELEASE_DATE));
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
