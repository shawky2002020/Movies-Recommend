package test;
import classes.*;

import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MovieRecommendationSystemTest {

    private final String testMoviesFile = "test_movies.txt";
    private final String testUsersFile = "test_users.txt";
    private final String testOutputFile = "recommendations.txt";

    @BeforeEach
    void resetData() throws Exception {
        Files.deleteIfExists(Path.of(testMoviesFile));
        Files.deleteIfExists(Path.of(testUsersFile));
        Files.deleteIfExists(Path.of(testOutputFile));

        // Reset internal static sets
        var movieField = Movie.class.getDeclaredField("usedSuffixes");
        movieField.setAccessible(true);
        ((java.util.Set<String>) movieField.get(null)).clear();

        var userField = User.class.getDeclaredField("usedIDs");
        userField.setAccessible(true);
        ((java.util.Set<String>) userField.get(null)).clear();
    }

    @AfterEach
    void cleanup() throws IOException {
        Files.deleteIfExists(Path.of(testMoviesFile));
        Files.deleteIfExists(Path.of(testUsersFile));
        Files.deleteIfExists(Path.of(testOutputFile));
    }

    @Test
    void testValidRecommendationIsWritten() throws IOException {
        // Valid input files
        Files.write(Path.of(testMoviesFile), List.of(
                "The Shawshank Redemption, TSR001",
                "Drama",
                "The Godfather, TG002",
                "Crime, Drama"
        ));

        Files.write(Path.of(testUsersFile), List.of(
                "Hassan Ali, 12345678X",
                "TSR001"
        ));

        MovieRecommendationSystem.generateRecommendations(testOutputFile,testMoviesFile, testUsersFile);

        List<String> lines = Files.readAllLines(Path.of(testOutputFile));
        assertTrue(lines.contains("Hassan Ali, 12345678X"));
        assertTrue(lines.contains("The Godfather"));
    }

    @Test
    void testErrorInMovieIsWritten() throws IOException {
        // Invalid movie title
        Files.write(Path.of(testMoviesFile), List.of(
                "the godfather, TG002",
                "Crime, Drama"
        ));

        Files.write(Path.of(testUsersFile), List.of(
                "Hassan Ali, 12345678X",
                "TG002"
        ));

        MovieRecommendationSystem.generateRecommendations(testOutputFile,testMoviesFile, testUsersFile);

        List<String> lines = Files.readAllLines(Path.of(testOutputFile));
        assertEquals("ERROR: Movie Title the godfather is wrong", lines.get(0));
    }

    @Test
    void testErrorInUserIsWritten() throws IOException {
        // Invalid user name
        Files.write(Path.of(testMoviesFile), List.of(
                "The Godfather, TG002",
                "Crime, Drama"
        ));

        Files.write(Path.of(testUsersFile), List.of(
                "ali123, 12345678X",
                "TG002"
        ));

        MovieRecommendationSystem.generateRecommendations(testOutputFile,testMoviesFile, testUsersFile);

        List<String> lines = Files.readAllLines(Path.of(testOutputFile));
        assertEquals("ERROR: User Name ali123 is wrong", lines.get(0));
    }

    @Test
    void testDuplicateMovieIdNumberError() throws IOException {
        Files.write(Path.of(testMoviesFile), List.of(
                "The Godfather, TG001",
                "Drama",
                "The Dark Knight, TDK001",
                "Action"
        ));

        Files.write(Path.of(testUsersFile), List.of(
                "Ali Mohamed, 12345678X",
                "TG001"
        ));

        MovieRecommendationSystem.generateRecommendations(testOutputFile,testMoviesFile, testUsersFile);

        List<String> lines = Files.readAllLines(Path.of(testOutputFile));
        assertEquals("ERROR: Movie Id numbers TDK001 aren’t unique", lines.get(0));
    }

    @Test
    void testDuplicateUserIdNumberError() throws IOException {
        Files.write(Path.of(testMoviesFile), List.of(
                "The Godfather, TG002",
                "Drama"
        ));

        Files.write(Path.of(testUsersFile), List.of(
                "Ali Mohamed, 12345678X",
                "TG002",
                "Omar Samir, 12345678X",
                "TG002"
        ));

        MovieRecommendationSystem.generateRecommendations(testOutputFile,testMoviesFile, testUsersFile);

        List<String> lines = Files.readAllLines(Path.of(testOutputFile));
        assertEquals("ERROR: User Id numbers 12345678X aren’t unique", lines.get(0));
    }
}
