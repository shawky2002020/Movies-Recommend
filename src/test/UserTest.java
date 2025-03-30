//package test;
//import classes.User;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
//class UserTest {
//
//    @Test
//    void testValidUserCreation() {
//        User user = new User("John Doe", "USR123");
//        assertEquals("John Doe", user.getName());
//        assertEquals("USR123", user.getUserId());
//    }
//
//    @Test
//    void testInvalidUserName() {
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//            new User("john doe", "USR123"); // Name should be capitalized
//        });
//        assertEquals("Invalid name format: Name must start with a capital letter", exception.getMessage());
//    }
//
//    @Test
//    void testInvalidUserId() {
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//            new User("John Doe", "123"); // User ID should have proper format
//        });
//        assertEquals("Invalid user ID format", exception.getMessage());
//    }
//
//    @Test
//    void testSetValidName() {
//        User user = new User("John Doe", "USR123");
//        user.setName("Jane Doe");
//        assertEquals("Jane Doe", user.getName());
//    }
//
//    @Test
//    void testSetInvalidName() {
//        User user = new User("John Doe", "USR123");
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//            user.setName("jane doe"); // Name must start with uppercase
//        });
//        assertEquals("Invalid name format: Name must start with a capital letter", exception.getMessage());
//    }
//
//    @Test
//    void testSetValidUserId() {
//        User user = new User("John Doe", "USR123");
//        user.setUserId("USR456");
//        assertEquals("USR456", user.getUserId());
//    }
//
//    @Test
//    void testSetInvalidUserId() {
//        User user = new User("John Doe", "USR123");
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//            user.setUserId("456"); // Invalid format
//        });
//        assertEquals("Invalid user ID format", exception.getMessage());
//    }
//}
