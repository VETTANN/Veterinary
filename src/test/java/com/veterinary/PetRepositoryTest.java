package com.veterinary;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PetRepositoryTest {

    @BeforeAll
    static void setUp() {
        String uri = System.getenv("MONGO_URI");
        if (uri == null || uri.isEmpty()) {
            uri = System.getProperty("spring.data.mongodb.uri");
        }
        if (uri == null || uri.isEmpty()) {
            uri = "mongodb+srv://yonimonigm_db_user:hZVNgo3zuURKjM3H@veterinary.5nopo6y.mongodb.net/Veterinary?retryWrites=true&w=majority";
        }

        MongoClient mongoClient = MongoClients.create(uri);
        MainApp.setMongoClient(mongoClient);
    }

    @Test
    void testPetFieldsLifecycle() {
        Pet testPet = new Pet();
        testPet.setName("Мурчик");
        testPet.setSpecies("кіт");
        
        assertEquals("Мурчик", testPet.getName());
        assertEquals("кіт", testPet.getSpecies());

        Pet pet1 = new Pet();
        pet1.setName("Барсік");
        pet1.setSpecies("кіт");

        Pet pet2 = new Pet();
        pet2.setName("Рекс");
        pet2.setSpecies("собака");

        List<Pet> petsList = Arrays.asList(pet1, pet2);

        assertEquals(2, petsList.size());
        assertEquals("Барсік", petsList.get(0).getName());
        assertEquals("собака", petsList.get(1).getSpecies());
    }
}