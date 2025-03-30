package classes;

public class test {
    public static void main(String[] args) {
        try {
            // Generate recommendations and write to file
            MovieRecommendationSystem.generateRecommendations();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
