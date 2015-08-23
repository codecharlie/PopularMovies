package com.polymorphic_solutions.popularmovies;

import android.graphics.Movie;
import android.net.Uri;
import android.os.AsyncTask;

import java.util.List;

/**
 * Created by cmorgan on 8/22/15.
 */
public class FetchMoviePostersTask extends AsyncTask<String, Void, List<Movie>> {
    private static final String LOG_TAG = FetchMoviePostersTask.class.getSimpleName();
    private static final String TMDB_API_KEY = "";
    private static final String POSTER_SIZE = "w185";
    private static final Uri    POSTER_BASE_URI = Uri.parse("http://image.tmdb.org/t/p/");

    public FetchMoviePostersTask() {
        super();
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        super.onPostExecute(movies);
    }

    @Override
    protected List<Movie> doInBackground(String... params) {
        return null;
    }
}
