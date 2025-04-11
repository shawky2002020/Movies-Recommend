package test;
import classes.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MovieTest {

    @BeforeEach
    void resetMovieIds() {
        // Reset the used suffixes via reflection (since usedSuffixes is private static)
        try {
            var field = Movie.class.getDeclaredField("usedSuffixes");
            field.setAccessible(true);
            ((java.util.Set<String>) field.get(null)).clear();
        } catch (Exception e) {
            throw new RuntimeException("Failed to reset Movie suffixes", e);
        }
    }

    @Test
    void testValidMovie() {
        assertDoesNotThrow(() ->
                new Movie("The Matrix", "TM123", Arrays.asList("Action", "Sci-Fi"))
        );
    }

    @Test
    void testInvalidTitleFormat() {
        Exception e = assertThrows(IllegalArgumentException.class, () ->
                new Movie("the matrix", "TM123", Arrays.asList("Action"))
        );
        assertEquals("ERROR: Movie Title the matrix is wrong", e.getMessage());
    }

    @Test
    void testInvalidMovieIdLetters() {
        Exception e = assertThrows(IllegalArgumentException.class, () ->
                new Movie("The Godfather", "GF123", Arrays.asList("Drama"))
        );
        assertEquals("ERROR: Movie Id letters GF123 are wrong", e.getMessage());
    }

    @Test
    void testDuplicateMovieIdSuffix() {
        assertDoesNotThrow(() ->
                new Movie("The Matrix", "TM123", Arrays.asList("Action"))
        );

        Exception e = assertThrows(IllegalArgumentException.class, () ->
                new Movie("The Dark Knight", "TDK123", Arrays.asList("Action"))
        );

        assertEquals("ERROR: Movie Id numbers TDK123 aren’t unique", e.getMessage());
    }
}
