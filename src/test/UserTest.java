package org.example;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**

 * Unit tests for the User class to validate equality, toString behavior, and integrity with varied data.

 */

public class UserTesting {

    // Checks equality of two identical users (same name, ID, and liked movie IDs)

    @Test

    public void testEqualityWithSameAttributes() {

        User user1 = new User("12345678A", "Alice Johnson", List.of("AJT001", "HDA002", "MSP003"));

        User user2 = new User("12345678A", "Alice Johnson", List.of("AJT001", "HDA002", "MSP003"));

        assertEquals(user1, user2);

    }

    // Validates that different user IDs result in inequality

    @Test

    public void testInequalityWithDifferentIds() {

        User user1 = new User("12345678A", "Alice Johnson", List.of("AJT001", "HDA002", "MSP003"));

        User user2 = new User("87654321B", "Alice Johnson", List.of("AJT001", "HDA002", "MSP003"));

        assertNotEquals(user1, user2);

    }

    // Validates that different user names result in inequality

    @Test

    public void testInequalityWithDifferentNames() {

        User user1 = new User("12345678A", "Alice Johnson", List.of("AJT001", "HDA002", "MSP003"));

        User user2 = new User("12345678A", "Robert Taylor", List.of("AJT001", "HDA002", "MSP003"));

        assertNotEquals(user1, user2);

    }

    // Validates that movie order doesn't affect equality

    @Test

    public void testEqualityWithDifferentMovieOrder() {

        User user1 = new User("12345678A", "Alice Johnson", List.of("HDA002", "AJT001", "MSP003"));

        User user2 = new User("12345678A", "Alice Johnson", List.of("AJT001", "HDA002", "MSP003"));

        assertEquals(user1, user2);

    }

    // Confirms that changing one movie ID breaks equality

    @Test

    public void testInequalityWithDifferentLikedMovieIds() {

        User user1 = new User("12345678A", "Alice Johnson", List.of("AJT001", "HDA002", "MSP003"));

        User user2 = new User("12345678A", "Alice Johnson", List.of("AJT001", "HDA002", "DRL004"));

        assertNotEquals(user1, user2);

    }

    // Validates equality of objects using assertTrue

    @Test

    public void testEqualsMethodReturnsTrue() {

        User user1 = new User("12345678A", "Alice Johnson", List.of("AJT001", "HDA002", "MSP003"));

        User user2 = new User("12345678A", "Alice Johnson", List.of("AJT001", "HDA002", "MSP003"));

        assertTrue(user1.equals(user2));

    }

    // Validates inequality of users using assertFalse

    @Test

    public void testEqualsMethodReturnsFalse() {

        User user1 = new User("12345678A", "Alice Johnson", List.of("AJT001", "HDA002", "MSP003"));

        User user2 = new User("87654321B", "Alice Johnson", List.of("AJT001", "HDA002", "MSP003"));

        assertFalse(user1.equals(user2));

    }

    // Verifies correct string representation from toString method

    @Test

    public void testToStringOutput() {

        User user = new User("12345678A", "Alice Johnson", List.of("AJT001", "HDA002", "MSP003"));

        String expected = "User{name='Alice Johnson', id='12345678A', likedMovieIds=[AJT001, HDA002, MSP003]}";

        assertEquals(expected, user.toString());

    }

    // Checks that comparing to null returns false

    @Test

    public void testNotEqualsWithNullObject() {

        User user = new User("12345678A", "Alice Johnson", List.of("AJT001", "HDA002", "MSP003"));

        assertNotEquals(null, user);

    }

}


