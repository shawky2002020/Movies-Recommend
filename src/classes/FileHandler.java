package classes;


import java.io.*;
import java.util.*;

public class FileHandler {

    public static List<Movie> readMovies(String filePath) throws IOException {
        List<Movie> movies = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] movieData = line.split(", ");
                if (movieData.length == 2) {
                    String title = movieData[0];
                    String id = movieData[1];
                    List<String> genres = new ArrayList<>();
                    if ((line = br.readLine()) != null) {
                        genres = Arrays.asList(line.split(", "));
                    }
                    movies.add(new Movie(title, id, genres));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException(e);

        }
        return movies;
    }

    public static List<User> readUsers(String filePath) throws IOException{
        List<User> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] userData = line.split(", ");
                if (userData.length == 2) {
                    String name = userData[0];
                    String id = userData[1];
                    List<String> watchedMovies = new ArrayList<>();
                    if ((line = br.readLine()) != null) {
                        watchedMovies = Arrays.asList(line.split(", "));
                    }
                    users.add(new User(name, id, watchedMovies));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException(e);

        }
        return users;
    }
}
