package classes;

import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class MovieRecommendationSystem {
    public static void generateRecommendations(String recommendationFile , String movieFile , String usersFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(recommendationFile))) {
            try {
            	

            	List<Movie> movies = FileHandler.readMovies(movieFile);
                List<User> users = FileHandler.readUsers(usersFile);
                if (movies.size()==0 || users.size() == 0) {
					writer.write( "ERROR: file is empty" );
					return;
				}

                // Generate recommendations (Placeholder logic)
                for (User user : users) {
                    writer.write(user.getName() + ", "+ user.getUserId() +"\n");

                    // Get watched genres
                    Set<String> watchedGenres = new HashSet<>();
                    for (String watchedMovieId : user.getWatchedMovies()) {
                        for (Movie movie : movies) {
                            if (movie.getMovieId().equals(watchedMovieId)) {
                                watchedGenres.addAll(movie.getGenres());
                            }
                        }
                    }

                    // Recommend movies in the same genres but not watched
                    for (Movie movie : movies) {
                        if (!user.getWatchedMovies().contains(movie.getMovieId())) {
                            for (String genre : movie.getGenres()) {
                                if (watchedGenres.contains(genre)) {
                                    writer.write(movie.getTitle() + "\n");
                                    break;
                                }
                            }
                        }
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
