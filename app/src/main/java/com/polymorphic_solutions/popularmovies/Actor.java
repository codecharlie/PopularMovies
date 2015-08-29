package com.polymorphic_solutions.popularmovies;

import java.util.ArrayList;

/**
 * Created by cmorgan on 8/22/15.
 *
 * The Actor Class will hold some brief information on the Actor/Actress for a movie.  The idea is to
 * later add the ability to select an actor/actress from a list and see all of their mMovies...
 *
 * It is important to note that the Movies, Actors, and Directors will have a Many-to-Many relationship
 * across the database tables...
 *
 */
public class Actor {
    private String nameFirst;
    private String nameLast;
    private String nameReal;
    private String bioSummary;
    private String birthdate;
    private ArrayList<MovieInfo> mMovies;

    public Actor(String nameFirst, String nameLast) {
        this.nameFirst = nameFirst;
        this.nameLast = nameLast;
    }

    public void setNameReal(String nameReal) {
        this.nameReal = nameReal;
    }

    public void setBioSummary(String bioSummary) {
        this.bioSummary = bioSummary;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public void setMovies(ArrayList<MovieInfo> movies) {
        this.mMovies = movies;
    }

    public String getNameFirst() {
        return nameFirst;
    }

    public String getNameLast() {
        return nameLast;
    }

    public String getNameReal() {
        return nameReal;
    }

    public String getBioSummary() {
        return bioSummary;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public ArrayList<MovieInfo> getMovies() {
        return mMovies;
    }
}
