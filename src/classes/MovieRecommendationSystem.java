package classes;

import java.io.*;
import java.util.List;

public class MovieRecommendationSystem {
    public static void generateRecommendations() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("recommendations.txt"))) {
            try {
                List<Movie> movies = FileHandler.readMovies("movies.txt");
                List<User> users = FileHandler.readUsers("users.txt");
                if (movies.size()==0 || users.size() == 0) {
					writer.write( "ERROR: file is empty" );
					return;
				}

                // Generate recommendations (Placeholder logic)
                for (User user : users) {
                    writer.write(user.getName() + "'s Recommendations:\n");
                    for (Movie movie : movies) {
                        writer.write(movie.getTitle() + "\n");
                    }
                    writer.write("\n");
                }
                writer.flush(); // Ensure data is written before closing

            } catch (IllegalArgumentException e) {
                // Write the first encountered error
                writer.write(e.getMessage() + "\n");
                writer.flush(); // Ensure the error is saved
                System.out.println( e.getMessage()); // Also print to console
            } catch (IOException e) {
                System.out.println("File error: " + e.getMessage());
            }
        } catch (IOException e) {
            System.out.println("Cannot write to recommendations file: " + e.getMessage());
        }
    }
}
