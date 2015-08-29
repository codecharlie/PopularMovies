package com.polymorphic_solutions.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.polymorphic_solutions.popularmovies.data.PopMovieContract.*;

/**
 * Created by cmorgan on 8/28/15.
 *
 * This class will help manage the database for the Popular Movies Application
 */
public class PopMovieDBHelper extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "movies.db";

    public PopMovieDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a table to hold locations.  A location consists of the string supplied in the
        // location setting, the city name, and the latitude and longitude
        final String SQL_CREATE_MOVIES_TABLE = "CREATE TABLE " + MovieEntry.TABLE_NAME + " (" +
                MovieEntry._ID + " INTEGER PRIMARY KEY, " +
                MovieEntry.COLUMN_MOVIE_ENTRY + " TEXT UNIQUE NOT NULL, " +
                MovieEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_SUMMARY + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_RATING + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_RELEASE_YEAR + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_POSTER + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_DIRECTOR_ID + " INTEGER NOT NULL, " +
                MovieEntry.COLUMN_DIRECTOR_NOTED + " INTEGER NOT NULL, " +
                MovieEntry.COLUMN_CERTIFICATION + " TEXT NOT NULL " +
                ");";

        final String SQL_CREATE_ACTORS_TABLE = "CREATE TABLE " + ActorEntry.TABLE_NAME + " (" +
                ActorEntry._ID + " INTEGER PRIMARY KEY, " +
                ActorEntry.COLUMN_ACTOR_ENTRY + " TEXT UNIQUE NOT NULL, " +
                ActorEntry.COLUMN_NAME_FIRST + " TEXT NOT NULL, " +
                ActorEntry.COLUMN_NAME_LAST + " TEXT NOT NULL, " +
                ActorEntry.COLUMN_NAME_REAL + " TEXT, " +
                ActorEntry.COLUMN_BIO + " TEXT NOT NULL, " +
                ActorEntry.COLUMN_BIRTHDAY + " TEXT NOT NULL " +
                ");";

        final String SQL_CREATE_GENRES_TABLE = "CREATE TABLE " + GenreEntry.TABLE_NAME + " (" +
                GenreEntry._ID + " INTEGER PRIMARY KEY, " +
                GenreEntry.COLUMN_GENRE_NAME + " TEXT NOT NULL " +
                ");";

        final String SQL_CREATE_DIRECTORS_TABLE = "CREATE TABLE " + DirectorEntry.TABLE_NAME + " (" +
                DirectorEntry._ID + "INTEGER PRIMARY KEY, " +
                DirectorEntry.COLUMN_DIRECTOR_ENTRY + " TEXT UNIQUE NOT NULL, " +
                DirectorEntry.COLUMN_NAME_FIRST + " TEXT NOT NULL, " +
                DirectorEntry.COLUMN_NAME_LAST + " TEXT NOT NULL, " +
                DirectorEntry.COLUMN_BIO + " TEXT NOT NULL, " +
                DirectorEntry.COLUMN_BIRTHDAY + " TEXT NOT NULL, " +
                DirectorEntry.COLUMN_KNOWN_CREDITS + " INTEGER NOT NULL " +
                ");";

        final String SQL_CREATE_MOVIE_ACTOR_TABLE = "CREATE TABLE " + MovieActorEntry.TABLE_NAME + " (" +
                MovieActorEntry._ID + " INTEGER PRIMARY KEY, " +
                MovieActorEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                MovieActorEntry.COLUMN_ACTOR_ID + " INTEGER NOT NULL, " +
                "FOREIGN KEY (" + MovieActorEntry.COLUMN_MOVIE_ID + ") REFERENCES " + MovieEntry.TABLE_NAME + " (" + MovieEntry._ID + "), " +
                "FOREIGN KEY (" + MovieActorEntry.COLUMN_ACTOR_ID + ") REFERENCES " + ActorEntry.TABLE_NAME + " (" + ActorEntry._ID + ") " +
                ");";

        final String SQL_CREATE_MOVIE_GENRE_TABLE = "CREATE TABLE " + MovieGenreEntry.TABLE_NAME + " (" +
                MovieGenreEntry._ID + " INTEGER PRIMARY KEY, " +
                MovieGenreEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                MovieGenreEntry.COLUMN_GENRE_ID + " INTEGER NOT NULL, " +
                "FOREIGN KEY (" + MovieGenreEntry.COLUMN_MOVIE_ID + ") REFERENCES " + MovieEntry.TABLE_NAME + " (" + MovieEntry._ID + "), " +
                "FOREIGN KEY (" + MovieGenreEntry.COLUMN_GENRE_ID + ") REFERENCES " + GenreEntry.TABLE_NAME + " (" + GenreEntry._ID + ") " +
                ");";

        db.execSQL(SQL_CREATE_MOVIES_TABLE);
        db.execSQL(SQL_CREATE_ACTORS_TABLE);
        db.execSQL(SQL_CREATE_GENRES_TABLE);
        db.execSQL(SQL_CREATE_DIRECTORS_TABLE);
        db.execSQL(SQL_CREATE_MOVIE_ACTOR_TABLE);
        db.execSQL(SQL_CREATE_MOVIE_GENRE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Since we are only usig the database for caching right now, we will drop and recreate the tables
        // if the database gets upgraded...  Later this might change as the app grows
        if (newVersion > oldVersion){
            db.execSQL("DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME + ";");
            db.execSQL("DROP TABLE IF EXISTS " + ActorEntry.TABLE_NAME + ";");
            db.execSQL("DROP TABLE IF EXISTS " + GenreEntry.TABLE_NAME + ";");
            db.execSQL("DROP TABLE IF EXISTS " + DirectorEntry.TABLE_NAME + ";");
            db.execSQL("DROP TABLE IF EXISTS " + MovieActorEntry.TABLE_NAME + ";");
            db.execSQL("DROP TABLE IF EXISTS " + MovieGenreEntry.TABLE_NAME + ";");

            // Finally, recreate all of the tables
            onCreate(db);
        }
    }
}
