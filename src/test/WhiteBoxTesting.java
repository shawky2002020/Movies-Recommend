package org.example;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// This test class covers unit tests for Movie, Validation, and User functionalities
public class WhiteBoxTesting {
    private final Validation validation = new Validation();


    // ───── Movie Tests ─────

    // Test: Equal movies with identical attributes should be equal
    @Test
    public void testMovieEquality_identicalMovies() {
        Movie movie1 = new Movie("MV101", "Eternal Echoes", List.of("Drama", "Fantasy"));
        Movie movie2 = new Movie("MV101", "Eternal Echoes", List.of("Drama", "Fantasy"));
        assertEquals(movie1, movie2); // Expect movies to be equal
    }

    // Test: Movies with same ID but different title should not be equal
    @Test
    public void testMovieInequality_differentTitles() {
        Movie movie1 = new Movie("LH202", "Lost Horizon", List.of("Mystery"));
        Movie movie2 = new Movie("SR202", "Shadow Realm", List.of("Mystery"));
        assertNotEquals(movie1, movie2); // Expect movies to differ by title
    }

    // Test: Movies with different IDs should not be equal
    @Test
    public void testMovieInequality_differentIDs() {
        Movie movie1 = new Movie("MV303", "Whispering Winds", List.of("Romance"));
        Movie movie2 = new Movie("MV404", "Whispering Winds", List.of("Romance"));
        assertNotEquals(movie1, movie2);
    }

    // Test: Movies with same title and ID but different genres should not be equal
    @Test
    public void testMovieInequality_differentGenres() {
        Movie movie1 = new Movie("MV505", "Crimson Sky", List.of("Action"));
        Movie movie2 = new Movie("MV505", "Crimson Sky", List.of("Action", "Sci-Fi"));
        assertNotEquals(movie1, movie2);
    }

    // Test: Genres in different order should still make movies equal
    @Test
    public void testMovieEquality_genreOrderIgnored() {
        Movie movie1 = new Movie("MV606", "Broken Silence", List.of("Thriller", "Horror"));
        Movie movie2 = new Movie("MV606", "Broken Silence", List.of("Horror", "Thriller"));
        assertEquals(movie1, movie2);
    }

    // Test: Movie toString output format
    @Test
    public void testMovieToString() {
        Movie movie = new Movie("MV707", "Echoes in the Mist", List.of("Drama", "Mystery"));
        String expected = "Movie{title='Echoes in the Mist', id='MV707', genres=[Drama, Mystery]}";
        assertEquals(expected, movie.toString());
    }

    // Test: Movie title setter and getter
    @Test
    public void testMovieSetGetTitle() {
        Movie movie = new Movie("MV808", "Placeholder", List.of("Drama"));
        movie.setMovieTitle("Shattered Light");
        assertEquals("Shattered Light", movie.getMovieTitle());
    }

    // Test: Movie ID setter and getter
    @Test
    public void testMovieSetGetID() {
        Movie movie = new Movie("MV909", "Silent Dawn", List.of("Thriller"));
        movie.setMovieID("MV910");
        assertEquals("MV910", movie.getMovieID());
    }

    // Test: Movie genres setter and getter
    @Test
    public void testMovieSetGetGenres() {
        Movie movie = new Movie("MV111", "Reflected Truth", List.of("Drama"));
        movie.setMovieGenres(List.of("Suspense", "Mystery"));
        assertEquals(List.of("Suspense", "Mystery"), movie.getMovieGenres());
    }

    // ───── Validation Tests ─────


    // Test: Valid username input should pass
    @Test
    public void testValidUsername() {
        validation.validateUsername("Sarah Johnson");
    }

    // Test: Invalid usernames should throw exception
    @Test
    public void testInvalidUsernames() {
        assertThrows(InputException.class, () -> validation.validateUsername(""));
        assertThrows(InputException.class, () -> validation.validateUsername(null));
        assertThrows(InputException.class, () -> validation.validateUsername("123Adam"));
    }

    // Test: Valid movie title input should pass
    @Test
    public void testValidMovieTitle() {
        validation.validateMovieTitle("Beyond Time");
    }

    // Test: Invalid movie titles should throw exception
    @Test
    public void testInvalidMovieTitles() {
        assertThrows(InputException.class, () -> validation.validateMovieTitle(""));
        assertThrows(InputException.class, () -> validation.validateMovieTitle("beyond time"));
    }

    // Test: Valid movie ID and title format
    @Test
    public void testValidMovieID() {
        validation.validateMovieID("BT123", "Beyond Time");
    }

    // Test: Invalid movie ID/title combinations
    @Test
    public void testInvalidMovieID() {
        assertThrows(InputException.class, () -> validation.validateMovieID("123BT", "Beyond Time"));
        assertThrows(InputException.class, () -> validation.validateMovieID("BT123", "beyond time"));
    }

    // ───── User Tests ─────

    // Test: Set and get username
    @Test
    public void testUserSetGetUsername() {
        User user = new User("US001", "Liam", List.of("MV001"));
        user.setUsername("Sophia");
        assertEquals("Sophia", user.getUsername());
    }

    // Test: Set and get user ID
    @Test
    public void testUserSetGetID() {
        User user = new User("US002", "Noah", List.of("MV002"));
        user.setUserID("US900");
        assertEquals("US900", user.getUserID());
    }

    // Test: Set and get liked movies
    @Test
    public void testUserSetGetLikedMovies() {
        User user = new User("US003", "Emma", List.of("MV003"));
        user.setLikedMovieIds(List.of("MV111", "MV222"));
        assertEquals(List.of("MV111", "MV222"), user.getLikedMovieIds());
    }

    // Test: User object instantiation
    @Test
    public void testUserNotNull() {
        User user = new User("US004", "Ava", List.of("MV004"));
        assertNotNull(user);
    }

    // Test: User toString output format
    @Test
    public void testUserToString() {
        User user = new User("US005", "Ethan", List.of("MV009", "MV010"));
        String expected = "User{name='Ethan', id='US005', likedMovieIds=[MV009, MV010]}";
        assertEquals(expected, user.toString());
    }
}