package classes;

import java.util.List;
import java.util.regex.Pattern;

public class Movie {
    private String title;
    private String movieId;
    private List<String> genres;

    public Movie(String title, String movieId, List<String> genres) throws IllegalArgumentException {
        if (!isValidTitle(title)) {
            throw new IllegalArgumentException("ERROR: Movie Title " + title + " is wrong");
        }
        if (!isValidMovieId(title, movieId)) {
            throw new IllegalArgumentException("ERROR: Movie Id " + movieId + " is wrong");
        }
        this.title = title;
        this.movieId = movieId;
        this.genres = genres;
    }

    public String getTitle() {
        return title;
    }

    public String getMovieId() {
        return movieId;
    }

    public List<String> getGenres() {
        return genres;
    }

    // Validate that every word starts with a capital letter
    private boolean isValidTitle(String title) {
        return Pattern.matches("([A-Z][a-z]*)(\\s[A-Z][a-z]*)*", title);
    }

    // Validate Movie ID format
    private boolean isValidMovieId(String title, String movieId) {
        String expectedPrefix = title.replaceAll("[^A-Z]", ""); // Extract capital letters from title
        System.out.println(expectedPrefix);
        String regex = "^" + expectedPrefix + "\\d{3}$";  // Ensure exact match
        return movieId.matches(regex);
    }

}



