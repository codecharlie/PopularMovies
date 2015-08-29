package com.polymorphic_solutions.popularmovies;

import android.net.Uri;

import java.util.ArrayList;

/**
 * Created by cmorgan on 8/22/15.
 *
 * The MovieInfo Class will hold all the on information about an individual movie
 */
public class MovieInfo {
    private static final String LOG_TAG = MovieInfo.class.getSimpleName();

    private String title;
    private String summary;
    private String rating;
    private String releaseYear;
    private ArrayList<Actor> actors;
    private Uri posterUri;

    public MovieInfo(String title) {
        this.title = title;
    }

    public MovieInfo(String title, String summary, String rating, String released, Uri posterUri) {
        this.title = title;
        this.summary = summary;
        this.rating = rating;
        this.releaseYear = released;
        this.posterUri = posterUri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public ArrayList<Actor> getActors() {
        return actors;
    }

    public void setActors(ArrayList<Actor> actors) {
        this.actors = actors;
    }

    public Uri getPosterUri() {
        return posterUri;
    }

    public void setPosterURL(Uri posterUri) {
        this.posterUri = posterUri;
    }

    public void addActor(Actor actor){
        this.actors.add(actor);
    }
}
