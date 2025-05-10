package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        Validation validation = new Validation();
        FileHandler fileHandler = new FileHandler(validation);

        List<Movie> movies = null;
        List<User> users = null;
        try {
            users = fileHandler.loadUsers("users.txt");
            movies = fileHandler.loadMovies("movies.txt");
        } catch (InputException e) {
            System.out.println(e.getMessage());
            fileHandler.writeOutput("recommendations.txt", null, e.getMessage());
            return;
        }

        if (movies == null || users == null) return;

        RecommendationFinder recommendationFinder = new RecommendationFinder(movies);
        List<Recommendation> recommendations = new ArrayList<>();

        for (User user: users) {
            List<String> movieTitles = recommendationFinder.recommend(user);
            recommendations.add(new Recommendation(user.getUserID(), user.getUsername(), movieTitles));
        }

        fileHandler.writeOutput("recommendations.txt", recommendations, null);
    }
}
