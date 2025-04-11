package classes;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class Movie {
    private String title;
    private String movieId;
    private List<String> genres;
    private static final Set<String> usedSuffixes = new HashSet<>();

    public Movie(String title, String movieId, List<String> genres) throws IllegalArgumentException {
        if (!isValidTitle(title)) {
            throw new IllegalArgumentException("ERROR: Movie Title " + title + " is wrong");
        }

        String expectedPrefix = title.replaceAll("[^A-Z]", "");
        String regex = "^" + expectedPrefix + "\\d{3}$";

        if (!movieId.matches(regex)) {
            throw new IllegalArgumentException("ERROR: Movie Id letters " + movieId + " are wrong");
        }

        String suffix = movieId.substring(movieId.length() - 3);
        if (usedSuffixes.contains(suffix)) {
            throw new IllegalArgumentException("ERROR: Movie Id numbers " + movieId + " aren’t unique");
        }

        usedSuffixes.add(suffix);

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

    private boolean isValidTitle(String title) {
        return Pattern.matches("([A-Z][a-z]*)(\\s[A-Z][a-z]*)*", title);
    }
}
