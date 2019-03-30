package app.ws.shared;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.Assert.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UtilsTest {

    @Autowired
    Utils utils;

    @BeforeEach
    void setUp() {

    }

    @Test
    final void generateUserId() {
        String userId = utils.generateUserId(30);
        String anotherUserId = utils.generateUserId(30);
        assertNotNull(userId);
        assertTrue(userId.length() == 30);
        assertTrue(!userId.equalsIgnoreCase(anotherUserId));
    }

    @Test
    final void hasTokenNotExpired() {
        String token = utils.generateEmailVerificationToken("4futrd4A655f1gsfg");
        assertNotNull(token);
        boolean hasTokenExpired = Utils.hasTokenExpired(token);
        assertFalse(hasTokenExpired);
    }

    @Test
    @Disabled
    final void hasTokenExpired() {
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0ZnV0cmQ0QTY1NWYxZ3NmZyIsImV4cCI6MTU1NDgyMTUzMn1.L7BeO2Eoe_OYaGdAcVMLHYmbuFvSqOWqkZpO1uYMrYF2kKrdD4u5Maje7BrYG6WqJXz2cf8Z9eLje0YEG7uxIB";
        boolean hasTokenExpired = Utils.hasTokenExpired(token);
        assertTrue(hasTokenExpired);
    }
}