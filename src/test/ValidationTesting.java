package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValidationTesting {

    private Validation validation;

    @BeforeEach
    void setUp() {
        // Setup new Validation instance before each test
        validation = new Validation();
    }

// ------------------ USER ID VALIDATION ------------------

    // Test: Valid user ID should not throw
    @Test
    void validUserId_shouldPassWithoutException() {
        assertDoesNotThrow(() -> validation.ValidUserID("98765432B"));
    }

    // Test: User ID with invalid length
    @Test
    void userIdWithInvalidLength_shouldThrowException() {
        assertThrows(InputException.class, () -> validation.ValidUserID("12AB"));
    }

    // Test: User ID with invalid alphanumeric pattern
    @Test
    void userIdWithInvalidFormat_shouldThrowException() {
        assertThrows(InputException.class, () -> validation.ValidUserID("ABCDEF123"));
    }

// ------------------ USERNAME VALIDATION ------------------

    // Test: Valid username should pass
    @Test
    void validUsername_shouldPassWithoutException() {
        assertDoesNotThrow(() -> validation.validateUsername("Charlie Brown"));
    }

    // Test: Username starting with space
    @Test
    void usernameStartingWithSpace_shouldThrowException() {
        InputException exception = assertThrows(InputException.class,
                () -> validation.validateUsername(" Alice"));
        assertTrue(exception.getMessage().contains("User Name"));
    }

    // Test: Empty username input
    @Test
    void emptyUsername_shouldThrowException() {
        InputException exception = assertThrows(InputException.class,
                () -> validation.validateUsername(""));
        assertTrue(exception.getMessage().contains("User Name"));
    }

    // Test: Null username input
    @Test
    void nullUsername_shouldThrowException() {
        InputException exception = assertThrows(InputException.class,
                () -> validation.validateUsername(null));
        assertTrue(exception.getMessage().contains("User Name"));
    }

// ------------------ USER VALIDATION ------------------

    // Test: Valid user object
    @Test
    void validUserObject_shouldPassWithoutException() {
        User user = new User("11223344X", "Diana Prince", List.of("MH456"));
        assertDoesNotThrow(() -> validation.uservalidation(user));
    }

    // Test: Duplicate user ID
    @Test
    void duplicateUserId_shouldThrowException() {
        User user1 = new User("44556677Z", "Clark Kent", List.of("SH789"));
        User user2 = new User("44556677Z", "Bruce Wayne", List.of("SH789"));
        validation.uservalidation(user1);
        assertThrows(InputException.class, () -> validation.uservalidation(user2));
    }

// ------------------ MOVIE TITLE VALIDATION ------------------

    // Test: Valid movie title with capitalized words
    @Test
    void movieTitleWithCapitalWords_shouldPass() {
        assertDoesNotThrow(() -> validation.validateMovieTitle("Guardians Of Galaxy"));
    }

    // Test: Title starting with lowercase
    @Test
    void movieTitleWithLowercaseStart_shouldThrowException() {
        assertThrows(InputException.class, () -> validation.validateMovieTitle("spider Man"));
    }

    // Test: Empty movie title
    @Test
    void emptyMovieTitle_shouldThrowException() {
        assertThrows(InputException.class, () -> validation.validateMovieTitle(""));
    }

    // Test: Null movie title
    @Test
    void nullMovieTitle_shouldThrowException() {
        assertThrows(InputException.class, () -> validation.validateMovieTitle(null));
    }

// ------------------ MOVIE ID VALIDATION ------------------

    // Test: Valid movie ID based on title
    @Test
    void validMovieIdForTitle_shouldPass() {
        assertDoesNotThrow(() -> validation.validateMovieID("GOG123", "Guardians Of Galaxy"));
    }

    // Test: Invalid movie ID mismatch with title initials
    @Test
    void invalidMovieIdMismatchWithTitle_shouldThrowException() {
        assertThrows(InputException.class, () -> validation.validateMovieID("XYZ123", "Guardians Of Galaxy"));
    }

// ------------------ MOVIE OBJECT VALIDATION ------------------

    // Test: Movie with valid genre and ID
    @Test
    void validMovieObject_shouldPass() {
        Movie movie = new Movie("ITC321", "Into The Cosmos", List.of("Sci-Fi", "Adventure"));
        assertDoesNotThrow(() -> validation.movievalidation(movie));
    }

    // Test: Movie with empty genre list
    @Test
    void movieWithEmptyGenres_shouldThrowException() {
        assertThrows(InputException.class,
                () -> validation.movievalidation(new Movie("EXP202", "Explorers", List.of())));
    }

    // Test: Movie with null genre list (treated as empty here)
    @Test
    void movieWithNullGenres_shouldThrowException() {
        assertThrows(InputException.class,
                () -> validation.movievalidation(new Movie("EXP202", "Explorers", List.of())));
    }

    // Test: Movie ID with same last 3 digits (non-unique)
    @Test
    void duplicateLastThreeDigitsMovieId_shouldThrowException() {
        Movie movie1 = new Movie("HRO111", "Heroic Rise", List.of("Action"));
        Movie movie2 = new Movie("ADV111", "Adventures Beyond", List.of("Adventure"));
        validation.movievalidation(movie1);
        assertThrows(InputException.class, () -> validation.movievalidation(movie2));
    }

    // Test: Movie ID with unique last 3 digits
    @Test
    void uniqueLastThreeDigitsMovieId_shouldPass() {
        Movie movie1 = new Movie("HRO111", "Heroic Rise", List.of("Action"));
        Movie movie2 = new Movie("ADV112", "Adventures Beyond", List.of("Adventure"));
        assertDoesNotThrow(() -> {
            validation.movievalidation(movie1);
            validation.movievalidation(movie2);
        });
    }

    // Test: Invalid short movie ID format
    @Test
    void shortMovieId_shouldThrowException() {
        assertThrows(InputException.class,
                () -> validation.movievalidation(new Movie("AB", "Short", List.of("Comedy"))));
    }
}
