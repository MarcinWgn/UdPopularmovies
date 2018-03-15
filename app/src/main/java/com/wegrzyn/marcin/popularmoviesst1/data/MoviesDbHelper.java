package com.wegrzyn.marcin.popularmoviesst1.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.wegrzyn.marcin.popularmoviesst1.data.MovieContract.MovieEntry;
/**
 * Created by Marcin Węgrzyn on 06.03.2018.
 * wireamg@gmail.com
 */

class MoviesDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movies.db";
    private static final int DATABASE_VERSION = 1;

    public MoviesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_MOVIES_TABLE =  "CREATE TABLE " + MovieEntry.TABLE_NAME
                + "("
//                +MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +MovieEntry.ID_MOVIE + " TEXT PRIMARY KEY, "
                +MovieEntry.TITLE+ " TEXT NOT NULL, "
                +MovieEntry.RELEASE_DATE + " TEXT NOT NULL, "
                +MovieEntry.POSTER_LOCAL+ " TEXT NOT NULL, "
                +MovieEntry.VOTE_AVERAGE+ " TEXT NOT NULL, "
                +MovieEntry.PLOT_SYNOPSIS+ " TEXT NOT NULL"
                + "); ";

        db.execSQL(CREATE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
