package org.example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class Movie {
    private String movieID;
    private String movieTitle;
    private List<String> movieGenres;

    public Movie(String movieID, String movieTitle, List<String> movieGenres) {
        this.movieID = movieID;
        this.movieTitle = movieTitle;
        this.movieGenres = new ArrayList<>(movieGenres);
    }

    public void setMovieID(String movieID) {
        this.movieID = movieID;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public void setMovieGenres(List<String> movieGenres) {
        this.movieGenres = new ArrayList<>(movieGenres);
    }

    public String getMovieID() {
        return movieID;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public List<String> getMovieGenres() {
        return new ArrayList<>(movieGenres);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieTitle, movieID, new HashSet<>(movieGenres));
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Movie movie)) return false;
        return Objects.equals(movieTitle, movie.movieTitle) && Objects.equals(movieID, movie.movieID) && Objects.equals(new HashSet<>(this.movieGenres), new HashSet<>(movie.movieGenres));
    }
    @Override
    public String toString() {
        return "Movie{" + "title='" + movieTitle + '\'' + ", id='" + movieID + '\'' + ", genres=" + movieGenres + '}';
    }
}


