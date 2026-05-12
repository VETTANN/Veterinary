package com.veterinary;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.lang.reflect.Field;
import org.junit.jupiter.api.Test;

class PetTest {

    @Test
    void toStringContainsAllImportantFields() throws Exception {
        Owner owner = new Owner(); 
        
        Pet pet = new Pet(owner, "Барсик", "Кіт");

        Field idField = Pet.class.getDeclaredField("petId");
        idField.setAccessible(true);
        idField.set(pet, 123); 

        String value = pet.toString();

        assertTrue(value.contains("petId=123"));
        assertTrue(value.contains("name='Барсик'"));
        assertTrue(value.contains("species='Кіт'"));
    }
}