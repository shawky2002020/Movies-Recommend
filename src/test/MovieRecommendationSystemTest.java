package test;
import classes.*;

import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MovieRecommendationSystemTest {
    private static final String MOVIES_FILE = "movies.txt";
    private static final String USERS_FILE = "users.txt";
    private static final String RECOMMENDATIONS_FILE = "recommendations.txt";

    @BeforeEach
    void setUp() throws IOException {
        // Ensure files are empty before each test
        Files.deleteIfExists(Paths.get(MOVIES_FILE));
        Files.deleteIfExists(Paths.get(USERS_FILE));
        Files.deleteIfExists(Paths.get(RECOMMENDATIONS_FILE));
    }

    @Test
    void testErrorWrittenForInvalidMovieTitle() throws IOException {
        // Writing invalid movie data
        Files.write(Paths.get(MOVIES_FILE), List.of(
                "the godfather, TG002",  // Invalid title (should start with uppercase)
                "Crime, Drama"
        ));
        Files.write(Paths.get(USERS_FILE), List.of(
                "Ali Mohamed, 12345678X",
                "TG002"
        ));

        MovieRecommendationSystem.generateRecommendations();

        // Check if error is written in recommendations.txt
        List<String> lines = Files.readAllLines(Paths.get(RECOMMENDATIONS_FILE));
        assertFalse(lines.isEmpty());
        assertEquals("ERROR: Movie Title the godfather is wrong", lines.get(0));
    }

    @Test
    void testErrorWrittenForInvalidUserName() throws IOException {
        // Writing invalid user data
        Files.write(Paths.get(MOVIES_FILE), List.of(
                "The Godfather, TG002",
                "Crime, Drama"
        ));
        Files.write(Paths.get(USERS_FILE), List.of(
                "Ali123, 12345678X",  // Invalid name (contains numbers)
                "TG002"
        ));

        MovieRecommendationSystem.generateRecommendations();

        // Check if error is written in recommendations.txt
        List<String> lines = Files.readAllLines(Paths.get(RECOMMENDATIONS_FILE));
        assertFalse(lines.isEmpty());
        assertEquals("ERROR: User Name Ali123 is wrong", lines.get(0));
    }

    @Test
    void testValidRecommendationsAreWritten() throws IOException {
        // Writing valid movie and user data
        Files.write(Paths.get(MOVIES_FILE), List.of(
                "The Shawshank Redemption, TSR001",
                "Drama",
                "The Godfather, TG002",
                "Crime, Drama"
        ));
        Files.write(Paths.get(USERS_FILE), List.of(
                "Hassan Ali, 12345678X",
                "TSR001, TG002"
        ));

        MovieRecommendationSystem.generateRecommendations();

        // Check recommendations content
        List<String> lines = Files.readAllLines(Paths.get(RECOMMENDATIONS_FILE));
        assertFalse(lines.isEmpty());
        assertEquals("Hassan Ali's Recommendations:", lines.get(0));
        assertEquals("The Shawshank Redemption", lines.get(1));
        assertEquals("The Godfather", lines.get(2));
    }

    @Test
    void testEmptyMoviesFile() throws IOException {
        // Empty movies file
        Files.write(Paths.get(MOVIES_FILE), List.of());
        Files.write(Paths.get(USERS_FILE), List.of(
                "Hassan Ali, 12345678X",
                "TSR001"
        ));

        MovieRecommendationSystem.generateRecommendations();

        List<String> lines = Files.readAllLines(Paths.get(RECOMMENDATIONS_FILE));
        assertFalse(lines.isEmpty());
        assertEquals("ERROR: file is empty", lines.get(0)); // Adjust error message based on actual implementation
    }

    @AfterEach
    void tearDown() throws IOException {
        // Cleanup test files
        Files.deleteIfExists(Paths.get(MOVIES_FILE));
        Files.deleteIfExists(Paths.get(USERS_FILE));
//        Files.deleteIfExists(Paths.get(RECOMMENDATIONS_FILE));
    }
}
