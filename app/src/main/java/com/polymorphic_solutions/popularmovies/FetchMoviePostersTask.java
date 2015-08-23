package com.polymorphic_solutions.popularmovies;

import android.graphics.Movie;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by cmorgan on 8/22/15.
 *
 * AsyncTask fo retreiving Movie Information and Posters
 */
public class FetchMoviePostersTask extends AsyncTask<String, Void, List<Movie>> {
    private static final String LOG_TAG = FetchMoviePostersTask.class.getSimpleName();
    private static final String TMDB_API_KEY = "f311557990ffbd2236fc2a463de956a9";
    private static final String POSTER_SIZE = "w185";
    private static final Uri    POSTER_BASE_URI = Uri.parse("http://image.tmdb.org/t/p/");
    private static final Uri    MOVIE_BASE_URI = Uri.parse("http://api.themoviedb.org/3/discover/movie?");

    public FetchMoviePostersTask() {
        super();
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        super.onPostExecute(movies);
    }

    @Override
    protected List<Movie> doInBackground(String... params) {
        // First we need to make sure we have been params we need
        if (params.length == null){
            Log.e(LOG_TAG, "Proc: doInBackground -- Not fed the required parameter");
            return null;
        }

        HttpURLConnection connection = null;
        BufferedReader reader = null;
        String jsonData = null;
        final String SORTBY = "sort_by";
        final String KEY = "api_key";
        String sortItem = params[0];

        try {
            Uri uri = MOVIE_BASE_URI.buildUpon()
                    .appendQueryParameter(SORTBY, sortItem)
                    .appendQueryParameter(KEY, TMDB_API_KEY)
                    .build();

            URL url = new URL(uri.toString());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            InputStream inStream = connection.getInputStream();
            if (inStream == null){
                Log.d(LOG_TAG, "Failed to get an InputStream from the URL");
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inStream));
            if (reader == null){
                Log.d(LOG_TAG, "Failed to get the BufferedReader");
                return null;
            }

            String line = "";
            StringBuffer buffer = new StringBuffer();
            while ((line = reader.readLine()) != null){
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0){
                Log.d(LOG_TAG, "No data was received from the URL");
                return null;
            }

            jsonData = buffer.toString();
        } catch (IOException e){
            Log.e(LOG_TAG, "There was an error in the doInBackground Proc: " + e);
            return null;
        }finally {
            if (connection != null){
                connection.disconnect();
            }
            if (reader != null){
                try{
                    reader.close();
                }catch(IOException e){
                    Log.e(LOG_TAG, "Threw an error while trying to close the reader: " + e);
                }
            }
        }
        try {
            return processJsonData(jsonData);
        }catch (JSONException e){
            Log.e(LOG_TAG, "There was an error processing the JSON Data: " e);
            return null;
        }
    }

    private List<Movie> processJsonData(String jsonData) throws JSONException {

    }
}
