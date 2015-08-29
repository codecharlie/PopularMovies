package com.polymorphic_solutions.popularmovies.data;

import android.content.ContentProvider;
import android.content.UriMatcher;
import android.database.sqlite.SQLiteQueryBuilder;

import com.polymorphic_solutions.popularmovies.data.PopMovieContract.*;

/**
 * Created by cmorgan on 8/28/15.
 */
public class PopMovieProvider extends ContentProvider {
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private PopMovieDBHelper mDbHelper;

    static final int MOVIE = 100;
    static final int MOVIE_WITH_ID = 101;
    static final int MOVIE_WITH_TITLE = 102;
    static final int MOVIE_WITH_GENRE = 103;
    static final int MOVIE_WITH_ACTOR = 104;
    static final int MOVIE_WITH_DIRECTOR = 105;
    static final int MOVIE_WITH_RELEASE_YEAR = 106;
    static final int MOVIE_WITH_DIRECTOR_AND_NOTED = 107;

    static final int ACTOR = 200;
    static final int ACTOR_WITH_ID = 201;
    static final int ACTOR_WITH_LAST_NAME = 202;
    static final int ACTOR_WITH_FIRST_NAME = 203;
    static final int ACTOR_WITH_FULL_NAME = 204;
    static final int ACTOR_WITH_BIRTHDAY = 205;
    static final int ACTOR_WITH_REAL_NAME = 206;

    static final int DIRECTOR = 300;
    static final int DIRECTOR_WITH_ID = 301;
    static final int DIRECTOR_WITH_LAST_NAME = 302;
    static final int DIRECTOR_WITH_FIRST_NAME = 303;
    static final int DIRECTOR_WITH_FULL_NAME = 304;
    static final int DIRECTOR_WITH_BIRTHDAY = 305;
    static final int DIRECTOR_WITH_CREDITS = 306;

    static final int GENRE = 400;
    static final int GENRE_WITH_ID = 401;
    static final int GENRE_WITH_NAME = 402;

    private static final SQLiteQueryBuilder sMovieGenreQueryBuilder;
    private static final SQLiteQueryBuilder sMovieActorQueryBuilder;
    private static final SQLiteQueryBuilder sMovieDirectorQueryBuilder;

    static {
        sMovieGenreQueryBuilder = new SQLiteQueryBuilder();
        sMovieActorQueryBuilder = new SQLiteQueryBuilder();
        sMovieDirectorQueryBuilder = new SQLiteQueryBuilder();

        // This is an inner-join --> movies._id = movie_genres.movie_id && movie_genres.genre_id = genres._id
        sMovieGenreQueryBuilder.setTables(MovieGenreEntry.TABLE_NAME +
                        " INNER JOIN " + MovieEntry.TABLE_NAME + " ON " +
                        MovieGenreEntry.TABLE_NAME + "." + MovieGenreEntry.COLUMN_MOVIE_ID + " = " + MovieEntry.TABLE_NAME + "." + MovieEntry._ID +
                        " INNER JOIN " + GenreEntry.TABLE_NAME + " ON " +
                        MovieGenreEntry.TABLE_NAME + "." + MovieGenreEntry.COLUMN_GENRE_ID + " = " + GenreEntry.TABLE_NAME + "." + GenreEntry._ID
        );

        // This is an inner-join --> movies._id = movie_actor.movie_id && movie_actor.actor_id = actors._id
        sMovieActorQueryBuilder.setTables(MovieActorEntry.TABLE_NAME +
                        " INNER JOIN " + MovieEntry.TABLE_NAME + " ON " +
                        MovieActorEntry.TABLE_NAME + "." + MovieActorEntry.COLUMN_MOVIE_ID + " = " + MovieEntry.TABLE_NAME + "." + MovieEntry._ID +
                        " INNER JOIN " + ActorEntry.TABLE_NAME + " ON " +
                        MovieActorEntry.TABLE_NAME + "." + MovieActorEntry.COLUMN_ACTOR_ID + " = " + ActorEntry.TABLE_NAME + "." + ActorEntry._ID

        );

        // This is an inner-join --> movies.director_id = directors._id
        sMovieDirectorQueryBuilder.setTables(MovieEntry.TABLE_NAME +
                        " INNER JOIN " + DirectorEntry.TABLE_NAME + " ON " +
                        MovieEntry.TABLE_NAME + "." + MovieEntry.COLUMN_DIRECTOR_ID + " = " + DirectorEntry.TABLE_NAME + "." + DirectorEntry._ID
        );

    }


}
