package test;
import classes.Movie;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Arrays;

public class MovieTest {

    @Test
    public void testValidMovie() {
        Movie movie = new Movie("The Matrix", "TM123", Arrays.asList("Action", "Sci-Fi"));
        assertEquals("The Matrix", movie.getTitle());
        assertEquals("TM123", movie.getMovieId());
    }

    @Test
    public void testInvalidMovieTitle() {
        try {
            new Movie("the matrix", "TMX123", Arrays.asList("Action", "Sci-Fi"));
            fail("Expected IllegalArgumentException for invalid title");
        } catch (IllegalArgumentException e) {
            assertEquals("ERROR: Movie Title the matrix is wrong", e.getMessage());
        }
    }

    @Test
    public void testInvalidMovieId() {
        try {
            new Movie("Inception", "INC12", Arrays.asList("Sci-Fi", "Thriller"));
            fail("Expected IllegalArgumentException for invalid movie ID");
        } catch (IllegalArgumentException e) {
            assertEquals("ERROR: Movie Id INC12 is wrong", e.getMessage());
        }
    }
}
