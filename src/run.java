
import classes.*;
public class run {
    public static void main(String[] args) {
        try {
            // Generate recommendations and write to file
            MovieRecommendationSystem.generateRecommendations("recommendations.txt","movies.txt","users.txt");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
