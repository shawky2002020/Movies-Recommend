package test;
import classes.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.List;

class FileHandlerTest {
    @Test
    void testReadMovies() throws IOException {
        List<Movie> movies = FileHandler.readMovies("movies.txt");
        assertFalse(movies.isEmpty());
        assertEquals("The Shawshank Redemption", movies.get(0).getTitle());
    }

    @Test
    void testReadUsers() throws IOException {
        List<User> users = FileHandler.readUsers("users.txt");
        assertFalse(users.isEmpty());
        assertEquals("Hassan Ali", users.get(0).getName());
    }

   
}
