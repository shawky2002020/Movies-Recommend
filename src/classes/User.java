package classes;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class User {
    private String name;
    private String userId;
    private List<String> watchedMovies;
    private static final Set<String> usedIDs = new HashSet<>();

    private static final Pattern USER_ID_PATTERN = Pattern.compile("^\\d{8}[A-Za-z0-9]$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-z]+(?: [A-Za-z]+)*$");

    public User(String name, String userId, List<String> watchedMovies) {
        if (!NAME_PATTERN.matcher(name).matches()) {
            throw new IllegalArgumentException("ERROR: User Name " + name + " is wrong");
        }

        if (!USER_ID_PATTERN.matcher(userId).matches()) {
            throw new IllegalArgumentException("ERROR: User Id " + userId + " is wrong");
        }

        if (usedIDs.contains(userId)) {
            throw new IllegalArgumentException("ERROR: User Id numbers " + userId + " aren’t unique");
        }

        usedIDs.add(userId);
        this.name = name;
        this.userId = userId;
        this.watchedMovies = watchedMovies;
    }

    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
    }

    public List<String> getWatchedMovies() {
        return watchedMovies;
    }
}
