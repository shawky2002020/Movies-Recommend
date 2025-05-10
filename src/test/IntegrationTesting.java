package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IntegrationTesting {

    private FileHandler fileHandler;
    private Validation validation;
    private Path tempDirectory;

    // Setup temporary environment before each test
    @BeforeEach
    void setUp() throws IOException {
        validation = new Validation();
        fileHandler = new FileHandler(validation);
        tempDirectory = Files.createTempDirectory("testDir");
    }

    // Cleanup temporary files after each test
    @AfterEach
    void tearDown() throws IOException {
        Files.walk(tempDirectory)
                .sorted((a, b) -> b.compareTo(a))
                .map(Path::toFile)
                .forEach(file -> {
                    if (!file.delete()) {
                        System.err.println("Failed to delete file: " + file.getAbsolutePath());
                    }
                });
    }

    // Test valid movie file format and content parsing
    @Test
    void testLoadValidMovies() throws IOException {
        Path file = tempDirectory.resolve("movies.txt");
        Files.write(file, List.of(
                "Inception,I001","Action,Mystery","Frozen,F007","Animation,Family"
        ));

        List<Movie> movies = fileHandler.loadMovies(file.toString());

        assertEquals(2, movies.size());
        assertEquals("I001", movies.get(0).getMovieID());
        assertTrue(movies.get(0).getMovieGenres().contains("Mystery"));
    }

    // Test loading malformed movie file (missing genre line)
    @Test
    void testFailMalformedMovies() throws IOException {
        Path file = tempDirectory.resolve("invalid_movies.txt");
        Files.write(file, List.of("JustTitle,ID999"));

        InputException exception = assertThrows(InputException.class, () -> fileHandler.loadMovies(file.toString()));
        assertTrue(exception.getMessage().contains("ERROR: Movie Id"));
    }

    // Test valid users file parsing and movie preferences
    @Test
    void testLoadValidUsers() throws IOException {
        Path file = tempDirectory.resolve("users.txt");
        Files.write(file, List.of(
                "Charlie,11112222C","I001,F007","Dana,33334444D","F007"
        ));

        List<User> users = fileHandler.loadUsers(file.toString());

        assertEquals(2, users.size());
        assertEquals("Charlie", users.get(0).getUsername());
        assertEquals("11112222C", users.get(0).getUserID());
        assertEquals(2, users.get(0).getLikedMovieIds().size());
    }

    // Test user file with duplicate user ID
    @Test
    void testFailDuplicateUserId() throws IOException {
        Path file = tempDirectory.resolve("duplicate_user_ids.txt");
        Files.write(file, List.of(
                "Charlie,11112222C","I001","Dana,11112222C","F007"
        ));

        InputException exception = assertThrows(InputException.class, () -> fileHandler.loadUsers(file.toString()));
        assertTrue(exception.getMessage().contains("User ID 11112222C is not unique"));
    }

    // Test generating recommendations file based on movies and users
    @Test
    void testGenerateRecommendationsFile() throws IOException {
        // Prepare movie input
        Path moviesFile = tempDirectory.resolve("movies.txt");
        Files.write(moviesFile, List.of(
                "Inception,I001","Action,Mystery","Frozen,F007","Animation,Family"
        ));

        // Prepare user input
        Path usersFile = tempDirectory.resolve("users.txt");
        Files.write(usersFile, List.of(
                "Charlie,11112222C","I001","Dana,33334444D","F007"
        ));

        // Load input
        fileHandler.loadMovies(moviesFile.toString());
        fileHandler.loadUsers(usersFile.toString());

        // Generate and verify recommendations
        Path recommendationsFile = tempDirectory.resolve("recommendations.txt");
        fileHandler.generateRecommendations(recommendationsFile.toString());

        List<String> lines = Files.readAllLines(recommendationsFile);
        assertEquals(4, lines.size());
        assertEquals("11112222C,Charlie", lines.get(0));
        assertEquals("Inception", lines.get(1));
        assertEquals("33334444D,Dana", lines.get(2));
        assertEquals("Frozen", lines.get(3));
    }
}