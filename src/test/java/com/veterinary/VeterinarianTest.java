package com.veterinary;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class VeterinarianTest {
    @Test
    void testVeterinarianFields() {
        Veterinarian vet = new Veterinarian();
        vet.setFullName("Петренко І.В.");
        vet.setExperienceYears(5);

        assertEquals("Петренко І.В.", vet.getFullName());
        assertEquals(5, vet.getExperienceYears());
    }
}