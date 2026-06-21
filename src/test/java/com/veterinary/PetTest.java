package com.veterinary;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PetTest {
    @Test
    void testPetGettersAndSetters() {
        Pet pet = new Pet();
        pet.setName("Рекс");
        pet.setSpecies("Собака");

        assertEquals("Рекс", pet.getName());
        assertEquals("Собака", pet.getSpecies());
    }
}