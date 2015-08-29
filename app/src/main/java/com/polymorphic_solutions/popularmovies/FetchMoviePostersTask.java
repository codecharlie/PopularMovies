package com.polymorphic_solutions.popularmovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by cmorgan on 8/22/15.
 *
 * AsyncTask fo retreiving MovieInfo Information and Posters
 */
public class FetchMoviePostersTask extends AsyncTask<String, Void, List<MovieInfo>> {
    private static final String LOG_TAG = FetchMoviePostersTask.class.getSimpleName();

    private FetchResponse delegate;

    private static final String TMDB_API_KEY = "";      // You can get this from tmdb.org...
    private static final String POSTER_SIZE = "w185";
    private static final Uri    POSTER_BASE_URI = Uri.parse("http://image.tmdb.org/t/p/");
    private static final Uri    MOVIE_BASE_URI = Uri.parse("http://api.themoviedb.org/3/discover/movie?");

    public FetchMoviePostersTask(FetchResponse delegate) {
        this.delegate = delegate;
    }

    @Override
    protected void onPostExecute(List<MovieInfo> movies) {
        if (movies != null && movies.size() > 0){
            delegate.onFetchCompleted(movies);
        }
    }

    @Override
    protected List<MovieInfo> doInBackground(String... params) {
        // First we need to make sure we have been params we need
        if (params.length == 0){
            Log.d(LOG_TAG, "Proc: doInBackground -- No sort order specified");
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
            Log.e(LOG_TAG, "There was an error processing the JSON Data: " + e);
            return null;
        }
    }

    private String getYearFromDate(String date){
        final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        final Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(df.parse(date));
            return Integer.toString(cal.get(Calendar.YEAR));
        }catch(ParseException e){
            Log.d(LOG_TAG, "There was a issue parsing the YEAR from " + date + " --> " +e);
            return null;
        }
    }

    private List<MovieInfo> processJsonData(String jsonData) throws JSONException {
        // the items we need to parse out of the Data
        final String MOVIES = "results";
        final String TITLE = "original_title";
        final String SUMMARY = "overview";
        final String RATING = "vote_average";
        final String RELEASED = "release_date";
        final String POSTER_URL = "poster_path";

        JSONObject object = new JSONObject(jsonData);
        JSONArray  movieArray = object.getJSONArray(MOVIES);
        int movieCount = movieArray.length();
        List<MovieInfo> movies = new ArrayList<MovieInfo>();

        for (int x = 0; x < movieCount; x++){
            JSONObject movie = movieArray.getJSONObject(x);
            String title = movie.getString(TITLE);
            String summary = movie.getString(SUMMARY);
            String rating = movie.getString(RATING);
            String releaseYear = getYearFromDate(movie.getString(RELEASED));
            Uri poster = POSTER_BASE_URI.buildUpon()
                    .appendEncodedPath(POSTER_SIZE)
                    .appendEncodedPath(movie.getString(POSTER_URL))
                    .build();

            movies.add(new MovieInfo(title, summary, rating, releaseYear, poster));
        }

        return movies;
    }

    public interface FetchResponse {
        void onFetchCompleted(List<MovieInfo> movies);
    }
}
