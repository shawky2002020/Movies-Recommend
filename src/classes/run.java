package classes;

public class run {
    public static void main(String[] args) {
        try {
            // Generate recommendations and write to file
            MovieRecommendationSystem.generateRecommendations("recommendations.txt","movies_test.txt","users_test.txt");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
