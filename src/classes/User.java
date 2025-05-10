package org.example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class User {
    private String userID;
    private String username;
    private List<String> likedMovieIds;

    public User(String userID, String username, List<String> likedMovieIds) {
        this.userID = userID;
        this.username = username;
        this.likedMovieIds = new ArrayList<>(likedMovieIds); // Defensive copy
    }

    // Getters
    public String getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public List<String> getLikedMovieIds() {
        return new ArrayList<>(likedMovieIds); // Defensive copy
    }

    // Setters
    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setLikedMovieIds(List<String> likedMovieIds) {
        this.likedMovieIds = new ArrayList<>(likedMovieIds); // Defensive copy
    }
    @Override
    public String toString() {
        return "User{" + "name='" + username + '\'' + ", id='" + userID + '\'' + ", likedMovieIds=" + likedMovieIds + '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, userID, new HashSet<>(likedMovieIds));
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof User user)) return false;
        return Objects.equals(username, user.username) && Objects.equals(userID, user.userID) && Objects.equals(new HashSet<>(this.likedMovieIds), new HashSet<>(user.likedMovieIds));
    }

   }
