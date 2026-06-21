package com.veterinary;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class VeterinarianTest {
    @Test
    void testMultipleVeterinarians() {
        Veterinarian vet1 = new Veterinarian("Петренко Ірина Василівна", 5);
        Veterinarian vet2 = new Veterinarian("Іваненко Андрій Миколайович", 3);
        Veterinarian vet3 = new Veterinarian("Мельник Оксана Петрівна", 7);

        List<Veterinarian> vety = Arrays.asList(vet1, vet2, vet3);

        assertEquals(3, vety.size());
        assertEquals("Петренко Ірина Василівна", vety.get(0).getFullName());
        assertEquals(3, vety.get(1).getExperienceYears());
        assertEquals(7, vety.get(2).getExperienceYears());
    }
}