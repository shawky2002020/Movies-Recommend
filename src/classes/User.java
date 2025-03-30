package classes;
import java.util.regex.Pattern;

public class User {
    private String name;
    private String userId;

    // User ID pattern: Must be exactly 9 characters, start with numbers, and end with at most one letter
    private static final Pattern USER_ID_PATTERN = Pattern.compile("^\\d{8}[A-Za-z0-9]$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-z]+(?: [A-Za-z]+)*$");

    public User(String name, String userId) {
        if (!NAME_PATTERN.matcher(name).matches()) {
            throw new IllegalArgumentException("ERROR: User Name " + name + " is wrong");
        }
        if (!USER_ID_PATTERN.matcher(userId).matches()) {
            throw new IllegalArgumentException("ERROR: User Id " + userId + " is wrong");
        }
        this.name = name;
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
    }
}
