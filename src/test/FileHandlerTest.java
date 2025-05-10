package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileHandlerTesting {

    private FileHandler fileHandler;
    private Path tempDir;
    private Validation mockValidation;

    @BeforeEach
    void startup() throws IOException {
        mockValidation = mock(Validation.class);
        fileHandler = new FileHandler(mockValidation);
        tempDir = Files.createTempDirectory("testDir");
    }
    // Cleans up by deleting the temporary directory after each test
    @AfterEach
    void tearDown() throws IOException {
        // Recursively deleting all files inside the temp directory after tests
        Files.walk(tempDir).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
    }

    // Verifies that an empty file returns an empty list of movies.
    @Test
    void testEmptyMoviesFileReturnsEmptyList() throws IOException {
        // Creating an empty movie file
        Path file = tempDir.resolve("empty_movies.txt");
        Files.createFile(file);
        List<Movie> movies = fileHandler.loadMovies(file.toString());
        assertTrue(movies.isEmpty(), "Expected the list to be empty since the file is empty");
    }

    // Verifies that a valid movie file loads the correct number and details of movies.
    @Test
    void testValidMoviesFileLoadsCorrectly() throws IOException {
        // Creating a movie file with valid movie data
        Path file = tempDir.resolve("movies.txt");
        Files.write(file, List.of(
                "Journey Beyond,JBX001","Adventure,Fantasy","Mystic River,MRV987","Drama,Thriller"
        ));
        List<Movie> movies = fileHandler.loadMovies(file.toString());
        assertEquals("JBX001", movies.get(0).getMovieID(), "Movie ID should match the file data");
        assertTrue(movies.get(0).getMovieGenres().contains("Fantasy"), "Expected genres to contain 'Fantasy'");
    }

    // Verifies that an empty user file returns an empty list of users.
    @Test
    void testEmptyUsersFileReturnsEmptyList() throws IOException {
        Path file = tempDir.resolve("empty_users.txt");
        Files.createFile(file);
        List<User> users = fileHandler.loadUsers(file.toString());
        assertTrue(users.isEmpty(), "Expected the list to be empty since the file is empty");
    }

    // Verifies that a valid user file loads the correct number and details of users.
    @Test
    void testValidUsersFileLoadsCorrectly() throws IOException {
        // Creating a users file with valid user data
        Path file = tempDir.resolve("users.txt");
        Files.write(file, List.of(
                "Charlie,5678CBA","JBX001,MRV987","David,1234DAB","XYZ123"
        ));
        List<User> users = fileHandler.loadUsers(file.toString());
        assertEquals(2, users.size(), "Expected 2 users to be loaded from the file");
        assertEquals("Charlie", users.get(0).getUsername(), "Expected the first user to be Charlie");
        assertEquals("5678CBA", users.get(0).getUserID(), "Expected the first user ID to be '5678CBA'");
        assertEquals(2, users.get(0).getLikedMovieIds().size(), "Expected the first user to have 2 liked movies");
    }

    // Verifies that the file contains the expected recommendation data.
    @Test
    void testWriteRecommendationsToFile() throws IOException {
        // Creating a list of recommendation data
        Path file = tempDir.resolve("recommendations.txt");
        List<Recommendation> recommendations = List.of(
                new Recommendation("5678CBA", "Charlie", List.of("Journey Beyond", "Inception")),
                new Recommendation("1234DAB", "David", List.of("Mystic River"))
        );
        fileHandler.writeOutput(file.toString(), recommendations, null);
        List<String> lines = Files.readAllLines(file);
        assertEquals("Charlie,5678CBA", lines.get(0), "Expected the first line to contain Charlie's data");
        assertEquals("Journey Beyond,Inception", lines.get(1), "Expected the second line to contain the recommended movies for Charlie");
        assertEquals("David,1234DAB", lines.get(2), "Expected the third line to contain David's data");
        assertEquals("Mystic River", lines.get(3), "Expected the fourth line to contain the movie recommended for David");
    }

    // Verifies that the file is created with no content when null values are passed.
    @Test
    void testWriteEmptyFileWithNullValues() throws IOException {
        Path file = tempDir.resolve("empty_output.txt");
        fileHandler.writeOutput(file.toString(), null, null);
        List<String> lines = Files.readAllLines(file);
        assertTrue(lines.isEmpty(), "Expected an empty file when passing null values");
    }

    // Verifies that the file contains the exact error message provided.
    @Test
    void testWriteErrorMessageToFile() throws IOException {
        // Error message to be written
        Path file = tempDir.resolve("recommendation_error.txt");
        String errorMsg = "ERROR: Movie Title XYZ is incorrect";
        fileHandler.writeOutput(file.toString(), null, errorMsg);
        List<String> lines = Files.readAllLines(file);
        assertEquals(1, lines.size(), "Expected exactly 1 line in the file with the error message");
        assertEquals(errorMsg, lines.get(0), "Expected the line to contain the specific error message");
    }

    // Verifies that a NullPointerException is thrown when required movie genres data is missing.
    @Test
    void testLoadMoviesWithMissingGenresThrowsException() throws IOException {
        Path file = tempDir.resolve("bad_movies.txt");
        Files.write(file, List.of("OnlyTitle,ID123"));
        assertThrows(NullPointerException.class, () -> fileHandler.loadMovies(file.toString()), "Expected a NullPointerException due to missing genres");
    }

    // Verifies that a NullPointerException is thrown when required liked movies data is missing.
    @Test
    void testLoadUsersWithMissingLikedMoviesThrowsException() throws IOException {
        Path file = tempDir.resolve("bad_users.txt");
        Files.write(file, List.of("User1,ID999"));
        assertThrows(NullPointerException.class, () -> fileHandler.loadUsers(file.toString()), "Expected a NullPointerException due to missing liked movies data");
    }

    // Verifies that trying to load a nonexistent user file throws an IOException.
    @Test
    void testLoadUsersFromNonexistentFileThrowsException() {
        assertThrows(IOException.class, () -> fileHandler.loadUsers("nonexistent.txt"), "Expected an IOException when trying to load a nonexistent file");
    }

    // Verifies that trying to load a nonexistent movie file throws an IOException.
    @Test
    void testLoadMoviesFromNonexistentFileThrowsException() {
        assertThrows(IOException.class, () -> fileHandler.loadMovies("nonexistent.txt"), "Expected an IOException when trying to load a nonexistent movie file");
    }
}
