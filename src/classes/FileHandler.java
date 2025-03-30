package classes;
import java.io.*;
import java.util.*;

public class FileHandler {
    public static List<Movie> readMovies(String filePath) throws IOException {
        List<Movie> movies = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(","); 
                if (parts.length < 3) continue; 

                String title = parts[0].trim();
                String movieId = parts[1].trim();
                List<String> genres = Arrays.asList(parts[2].trim().split("\\|"));

                try {
                    movies.add(new Movie(title, movieId, genres));
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage()); 
                }
            }
        }
        return movies;
    }

    public static List<User> readUsers(String filePath) throws IOException {
        List<User> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(","); 
                if (parts.length < 2) continue; 

                String name = parts[0].trim();
                String userId = parts[1].trim();

                try {
                    users.add(new User(name, userId));
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage()); 
                }
            }
        }
        return users;
    }
}
