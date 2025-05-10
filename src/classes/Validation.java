package org.example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;


public class Validation {

    private final List<Movie> movies = new ArrayList<>();
    private final List<User> users = new ArrayList<>();
    private static final Pattern USER_ID_p = Pattern.compile("^\\d{8}[A-Z0-9]");



//    @Override
    public void movievalidation(Movie movie) {
        validateMovieTitle(movie.getMovieTitle());
        validateGenre(movie.getMovieGenres());
        movies.add(movie);
        validateMovieId();
    }

    private void validateUserIdUniqueness() {
        Set<String> seenIds = new HashSet<>();
        for (User user : users) {
            String userId = user.getUserID();
            if (seenIds.contains(userId)) {
                throw new InputException("User ID " + userId + " is not unique");
            }
            seenIds.add(userId);
        }
    }

    private void validateMovieId() {
        for (int i = 0; i < movies.size(); i++) {
            Movie Cmovie = movies.get(i);
            String LastThreeDigits = getLastThreeDigits(Cmovie.getMovieID());
            for (int j = i + 1; j < movies.size(); j++) {
                Movie Nmovie = movies.get(j);
                String LastThreeDigits2 = getLastThreeDigits(Nmovie.getMovieID());
                if (LastThreeDigits.equals(LastThreeDigits2)) {
                    throw new InputException("Movie ID numbers " + Cmovie.getMovieID() + " aren't unique");
                }
            }
        }
    }

    private String getLastThreeDigits(String movieId) {
        if (movieId == null || movieId.length() < 3) {
            throw new InputException("Invalid Movie ID format: " + movieId);
        }
        return movieId.substring(movieId.length() - 3);
    }


//    @Override
    public void uservalidation(User user) {
        validateUsername(user.getUsername());
        ValidUserID(user.getUserID());
        users.add(user);
        validateUserIdUniqueness();
    }

    public void validateUsername(String name) {
        // Doesn't allow space at the start. Space at the end is accepted
        if (name == null || name.isEmpty() || !name.matches("[A-Za-z][A-Za-z ]*")) {
            throw new InputException("Error: User Name " + name + " is Wrong");
        }
    }

    public void validateMovieTitle(String title) {
        if (title == null || title.isEmpty()) {
            throw new InputException("ERROR: Movie Title " + title + " is wrong");
        }
        String[] words = title.split(" ");
        for (String word : words) {
            if (!Character.isUpperCase(word.charAt(0))) {
                throw new InputException("ERROR: Movie Title " + title + " is wrong");
            }
        }
    }

    public void validateMovieID(String ID, String title) {
        StringBuilder capitalLetters = new StringBuilder();
        String[] words = title.split(" ");
        for (String word : words) {
            capitalLetters.append(word.charAt(0));
        }
        String IDStringPart = ID.substring(0, ID.length()-3);
        if (!capitalLetters.toString().equals(IDStringPart)) {
            throw new InputException("ERROR: Movie Id " + ID + " is wrong");
        }
    }

    public void ValidUserID(String ID) {
        if (ID.length() != 9 || !USER_ID_p.matcher(ID).matches()) {
            throw new InputException("ERROR: User ID " + ID + " is wrong");
        }
    }

    private void validateGenre(List<String> genres) {
        if (genres == null || genres.isEmpty()) {
            throw new InputException("Error:Movie genre is empty");
        }
    }
    public List<Movie> getMovies() {
        return new ArrayList<>(movies);
    }
    
    public List<User> getUsers() {
        return new ArrayList<>(users);
    }
}
