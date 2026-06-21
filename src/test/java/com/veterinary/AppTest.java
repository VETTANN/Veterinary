package com.veterinary;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AppTest {

    @BeforeAll
    static void setupTestMongoUri() {
        String uri = System.getenv("MONGO_URI");
        if (uri == null || uri.isEmpty()) {
            uri = "mongodb+srv://yonimonigm_db_user:hZVNgo3zuURKjM3H@veterinary.5nopo6y.mongodb.net/Veterinary?retryWrites=true&w=majority";
        }
        System.setProperty("spring.data.mongodb.uri", uri);
    }

    @Test
    void testApp() {
        assertTrue(true);
    }
}