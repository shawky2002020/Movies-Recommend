package org.example;

import java.util.ArrayList;
import java.util.List;

public record Recommendation(String userId, String username, List<String> recommendedMovieTitles) {
    public Recommendation(String userId, String username, List<String> recommendedMovieTitles) {
        this.userId = userId;
        this.username = username;
        this.recommendedMovieTitles = new ArrayList<>(recommendedMovieTitles);
    }

    @Override
    public List<String> recommendedMovieTitles() {
        return new ArrayList<>(recommendedMovieTitles);
    }

    @Override
    public String toString() {
        return "Recommendation{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", recommendedMovieTitles=" + recommendedMovieTitles +
                '}';
    }
}
