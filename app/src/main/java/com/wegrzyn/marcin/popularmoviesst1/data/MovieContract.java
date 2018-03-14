package com.wegrzyn.marcin.popularmoviesst1.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Marcin Węgrzyn on 06.03.2018.
 * wireamg@gmail.com
 */

public class MovieContract {

    static final String CONTENT_AUTHORITY = "com.wegrzyn.marcin.popularmoviesst1";
    static final String PATH_MOVIES = "movies";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


    public MovieContract() {
    }

    public static final class MovieEntry implements BaseColumns{


        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_MOVIES);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;

        public static final String _ID = BaseColumns._ID;
        public static final String ID_MOVIE = "id_movie";
        public static final String TITLE = "title";
        public static final String RELASE_DATE ="relase_date";
        public static final String POSTER_LOCAL="poster_local";
        public static final String VOTE_AVERAGE="vote_average";
        public static final String PLOT_SYNOPSIS="plot_synopsis";
        static final String TABLE_NAME="movies";
    }

    public static Uri getUriMovie(String idMovie){
        return Uri.withAppendedPath(MovieContract.MovieEntry.CONTENT_URI,idMovie);
    }
}
