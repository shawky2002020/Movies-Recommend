package test;
import classes.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @BeforeEach
    void resetUserIds() {
        // Reset usedIDs using reflection
        try {
            var field = User.class.getDeclaredField("usedIDs");
            field.setAccessible(true);
            ((java.util.Set<String>) field.get(null)).clear();
        } catch (Exception e) {
            throw new RuntimeException("Failed to reset User IDs", e);
        }
    }

    @Test
    void testValidUser() {
        assertDoesNotThrow(() ->
                new User("Hassan Ali", "12345678X", Arrays.asList("TSR001"))
        );
    }

    @Test
    void testInvalidUserName() {
        Exception e = assertThrows(IllegalArgumentException.class, () ->
                new User(" ali", "12345678X", Arrays.asList("TSR001"))
        );
        assertEquals("ERROR: User Name  ali is wrong", e.getMessage());
    }

    @Test
    void testInvalidUserIdFormat() {
        Exception e = assertThrows(IllegalArgumentException.class, () ->
                new User("Ali Mohamed", "123456", Arrays.asList("TSR001"))
        );
        assertEquals("ERROR: User Id 123456 is wrong", e.getMessage());
    }

    @Test
    void testDuplicateUserId() {
        assertDoesNotThrow(() ->
                new User("Ali Mohamed", "12345678X", Arrays.asList("TSR001"))
        );

        Exception e = assertThrows(IllegalArgumentException.class, () ->
                new User("Omar Samir", "12345678X", Arrays.asList("TG002"))
        );

        assertEquals("ERROR: User Id numbers 12345678X aren’t unique", e.getMessage());
    }
}
