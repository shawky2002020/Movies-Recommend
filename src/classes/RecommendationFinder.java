package org.example;

import java.util.*;

public class RecommendationFinder {

    private Map<String, List<String>> genreMovieTitlesMap = new HashMap<>();
    private List<Movie> movies;


    public RecommendationFinder(List<Movie> movies) {
        this.movies = movies;

        Set<String> allGenres = new HashSet<>();

        for (Movie movie: movies) {
            allGenres.addAll(movie.getMovieGenres());
        }

        for (String genre: allGenres) {
            List<String> genreMovieTitles = new ArrayList<>();
            for (Movie movie: movies) {
                if (movie.getMovieGenres().contains(genre)) {
                    genreMovieTitles.add(movie.getMovieTitle());
                }
            }
            genreMovieTitlesMap.put(genre, genreMovieTitles);
        }
    }

    public List<String> recommend(User user) {
        // Use a set to remove duplicates
        Set<String> recommendations = new HashSet<>();
        Set<String> likedGenres = new HashSet<>();

        // Get all user's liked genres
        for (String movieId: user.getLikedMovieIds()) {
            Movie movie = movies.stream().filter(m -> Objects.equals(m.getMovieID(), movieId)).findFirst().orElse(null);
            if (movie != null)
                likedGenres.addAll(movie.getMovieGenres());
        }

        // Get all movies with the same genres
        for (String likedGenre: likedGenres) {
            recommendations.addAll(genreMovieTitlesMap.get(likedGenre));
        }

        return new ArrayList<>(recommendations);
    }
}
