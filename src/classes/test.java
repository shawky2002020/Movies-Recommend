package classes;
import java.io.IOException;
import java.util.List;

public class test {
    public static void main(String[] args) {
        try {
            List<Movie> movies = FileHandler.readMovies("movies.txt");
            System.out.println("Loaded Movies:");
            for (Movie movie : movies) {
                System.out.println(movie.getTitle() + " - " + movie.getMovieId());
            }

            List<User> users = FileHandler.readUsers("users.txt");
            System.out.println("\nLoaded Users:");
            for (User user : users) {
                System.out.println(user.getName() + " - " + user.getUserId());
            }

        } catch (IOException e) {
            System.out.println("File reading error: " + e.getMessage());
        }
    }
}
