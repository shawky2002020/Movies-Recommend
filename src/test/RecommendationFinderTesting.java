package org.example;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertTrue;

// Unit tests for RecommendationFinder

class RecommendationFinderTesting {

    private List<Movie> movies;

    private RecommendationFinder finder;

    // Set up test data

    @BeforeEach

    void setUp() {

        movies = List.of(

                new Movie("THG101", "The Hidden Gem", List.of("Drama", "Mystery")),

                new Movie("DWS202", "Dark War Storm", List.of("Action", "Thriller")),

                new Movie("BNS303", "Bright Night Silence", List.of("Horror")),

                new Movie("COS404", "Code Of Shadows", List.of("Sci-Fi", "Action")),

                new Movie("FTM505", "For The Memories", List.of("Romance", "Drama")),

                new Movie("LSH606", "Last Silent Hour", List.of("Horror", "Thriller"))

        );

        finder = new RecommendationFinder(movies);

    }

    @Test

    void recommendsMoviesWithMultipleGenresCorrectly() {

        // User likes Drama and Mystery

        User user = new User("81234567A", "Sarah Connor", List.of("THG101"));

        List<String> recommendations = finder.recommend(user);

        assertTrue(recommendations.contains("The Hidden Gem"));

        assertTrue(recommendations.contains("For The Memories"));

        assertEquals(2, recommendations.size());

    }

    @Test

    void recommendsBasedOnSharedGenresWithNoDuplicates() {

        // User likes Sci-Fi and Action

        User user = new User("71234567B", "James Taylor", List.of("COS404"));

        List<String> recommendations = finder.recommend(user);

        assertTrue(recommendations.contains("Code Of Shadows"));

        assertTrue(recommendations.contains("Dark War Storm"));

        assertEquals(2, recommendations.size());

        assertEquals(new HashSet<>(recommendations).size(), recommendations.size()); // Ensure no duplicates

    }

    @Test

    void returnsEmptyListWhenUserLikesUnknownMovie() {

        // User likes a movie with no genres

        List<Movie> moviesWithUnknown = new ArrayList<>(movies);

        moviesWithUnknown.add(new Movie("NKM707", "No Known Movie", Collections.emptyList()));

        RecommendationFinder testEngine = new RecommendationFinder(moviesWithUnknown);

        User user = new User("61234567C", "Emily Rose", List.of("NKM707"));

        List<String> recommendations = testEngine.recommend(user);

        assertTrue(recommendations.isEmpty());

    }

    @Test

    void recommendsMoviesInSharedGenreAcrossMultipleLikes() {

        // User likes Horror and Thriller

        User user = new User("51234567D", "Michael Ford", List.of("LSH606"));

        List<String> recommendations = finder.recommend(user);

        assertTrue(recommendations.contains("Last Silent Hour"));

        assertTrue(recommendations.contains("Bright Night Silence"));

        assertTrue(recommendations.contains("Dark War Storm")); // Thriller

        assertEquals(3, recommendations.size());

    }

    @Test

    void handlesEmptyMovieDatabaseGracefully() {

        // Empty movie list should return no recommendations

        RecommendationFinder emptyFinder = new RecommendationFinder(Collections.emptyList());

        User user = new User("41234567E", "Natalie Pierce", List.of("XYZ999"));

        List<String> recommendations = emptyFinder.recommend(user);

        assertTrue(recommendations.isEmpty());

    }

    @Test

    void recommendsMoviesFromMultipleUserLikedMovies() {
        // User likes Action and Drama from different movies
        User user = new User("31234567F", "Liam Carter", List.of("DWS202", "FTM505"));

        List<String> recommendations = finder.recommend(user);

        assertTrue(recommendations.contains("Dark War Storm"));

        assertTrue(recommendations.contains("For The Memories"));

        assertTrue(recommendations.contains("Code Of Shadows"));

        assertTrue(recommendations.contains("The Hidden Gem"));

        assertEquals(5, recommendations.size());
    }

    @Test

    void returnsEmptyListWhenUserHasNoLikedMovies() {

        // User hasn't liked any movies yet

        User user = new User("21234567G", "Olivia Bennett", Collections.emptyList());

        List<String> recommendations = finder.recommend(user);

        assertTrue(recommendations.isEmpty());

    }

    @Test

    void recommendsCorrectlyFromSingleGenre() {

        // User likes Horror

        User user = new User("11234567H", "Daniel Craig", List.of("BNS303"));

        List<String> recommendations = finder.recommend(user);

        assertTrue(recommendations.contains("Bright Night Silence"));

        assertTrue(recommendations.contains("Last Silent Hour"));

        assertEquals(2, recommendations.size());

    }

}
