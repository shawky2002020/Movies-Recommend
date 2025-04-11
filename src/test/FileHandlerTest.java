package test;
import classes.*;

import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileHandlerTest {

    private final String tempMoviesFile = "temp_movies.txt";
    private final String tempUsersFile = "temp_users.txt";

    @BeforeEach
    void cleanUpUsedIds() throws Exception {
        // Reset Movie and User static sets before each test
        var movieField = Movie.class.getDeclaredField("usedSuffixes");
        movieField.setAccessible(true);
        ((java.util.Set<String>) movieField.get(null)).clear();

        var userField = User.class.getDeclaredField("usedIDs");
        userField.setAccessible(true);
        ((java.util.Set<String>) userField.get(null)).clear();
    }

    @AfterEach
    void deleteTempFiles() throws IOException {
        Files.deleteIfExists(Path.of(tempMoviesFile));
        Files.deleteIfExists(Path.of(tempUsersFile));
    }

    @Test
    void testReadValidMovies() throws IOException {
        Files.write(Path.of(tempMoviesFile), List.of(
                "The Shawshank Redemption, TSR001",
                "Drama",
                "The Godfather, TG002",
                "Crime, Drama"
        ));

        List<Movie> movies = FileHandler.readMovies(tempMoviesFile);

        assertEquals(2, movies.size());
        assertEquals("TSR001", movies.get(0).getMovieId());
    }

    @Test
    void testReadValidUsers() throws IOException {
        Files.write(Path.of(tempUsersFile), List.of(
                "Hassan Ali, 12345678X",
                "TSR001, TG002"
        ));

        List<User> users = FileHandler.readUsers(tempUsersFile);

        assertEquals(1, users.size());
        assertEquals("Hassan Ali", users.get(0).getName());
    }

    @Test
    void testMoviesFileNotFound() {
        assertThrows(IOException.class, () ->
                FileHandler.readMovies("nonexistent_movies.txt"));
    }

    @Test
    void testUsersFileNotFound() {
        assertThrows(IOException.class, () ->
                FileHandler.readUsers("nonexistent_users.txt"));
    }

    @Test
    void testInvalidMovieThrowsError() throws IOException {
        Files.write(Path.of(tempMoviesFile), List.of(
                "the godfather, TG002",  // invalid title
                "Crime, Drama"
        ));

        Exception e = assertThrows(IllegalArgumentException.class, () ->
                FileHandler.readMovies(tempMoviesFile)
        );

        assertEquals("ERROR: Movie Title the godfather is wrong", e.getMessage());
    }

    @Test
    void testInvalidUserThrowsError() throws IOException {
        Files.write(Path.of(tempUsersFile), List.of(
                "ali123, 12345678X",  // invalid name
                "TSR001"
        ));

        Exception e = assertThrows(IllegalArgumentException.class, () ->
                FileHandler.readUsers(tempUsersFile)
        );

        assertEquals("ERROR: User Name ali123 is wrong", e.getMessage());
    }
}
