package org.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    Validation validation;

    public FileHandler(Validation validation) {
        this.validation = validation;
    }

    // Load and validate movies from file
    public List<Movie> loadMovies(String filePath) throws IOException {
        List<Movie> movies = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String titleIdLine;
            while ((titleIdLine = reader.readLine()) != null) {
                String genresLine = reader.readLine();

                // Read title and ID, then validate
                String[] titleIdParts = titleIdLine.split(",");
                String title = titleIdParts[0].trim();
                validation.validateMovieTitle(title);

                String id = titleIdParts[1].trim();
                validation.validateMovieID(id, title);

                // Parse genre list
                String[] genreParts = genresLine.split(",");
                List<String> genres = new ArrayList<>();
                for(String genre : genreParts) {
                    genres.add(genre.trim());
                }

                // Validate full movie object
                Movie movie = new Movie(id, title, genres);
                validation.movievalidation(movie);
                movies.add(movie);
            }
        }
        return movies;
    }

    // Load and validate users from file
    public List<User> loadUsers(String filePath) throws IOException {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String nameIdLine;
            while ((nameIdLine = reader.readLine()) != null) {
                String likedMoviesLine = reader.readLine();

                // Read and validate username and ID
                String[] nameIdParts = nameIdLine.split(",");
                String name = nameIdParts[0];
                validation.validateUsername(name);

                String id = nameIdParts[1].trim();
                validation.ValidUserID(id);

                // Parse liked movie IDs
                List<String> likedMovies = new ArrayList<>();
                String[] likedMovieParts = likedMoviesLine.split(",");
                for (String movieId : likedMovieParts) {
                    if (!movieId.trim().isEmpty()) {
                        likedMovies.add(movieId.trim());
                    }
                }

                // Validate full user object
                User user = new User(id, name, likedMovies);
                validation.uservalidation(user);
                users.add(user);
            }
        }
        return users;
    }

    // Write recommendations or error to file
    public void writeOutput(String filePath, List<Recommendation> recommendations, String errorMessage) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            if (errorMessage != null) {
                writer.write(errorMessage);
                writer.newLine();
            } else if (recommendations != null) {
                for (Recommendation recommendation : recommendations) {
                    writer.write(recommendation.username() + "," + recommendation.userId());
                    writer.newLine();
                    writer.write(String.join(",", recommendation.recommendedMovieTitles()));
                    writer.newLine();
                }
            } else {
                System.err.println("Warning: No recommendations or error message provided for writing to " + filePath);
            }
        }
    }

    // Generate recommendations based on user liked movies
    public void generateRecommendations(String filePath) throws IOException {
        List<Recommendation> recommendations = new ArrayList<>();

        for (User user : validation.getUsers()) {
            List<String> recommendedMovies = new ArrayList<>();
            for (String likedMovieId : user.getLikedMovieIds()) {
                for (Movie movie : validation.getMovies()) {
                    if (movie.getMovieID().equals(likedMovieId)) {
                        recommendedMovies.add(movie.getMovieTitle());
                    }
                }
            }
            recommendations.add(new Recommendation(user.getUsername(), user.getUserID(), recommendedMovies));
        }

        writeOutput(filePath, recommendations, null);
    }
}
