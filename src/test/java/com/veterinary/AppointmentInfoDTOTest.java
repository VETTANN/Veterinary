package com.veterinary;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

class AppointmentInfoDTOTest {

    @Test
    void testConstructorAndGetters() {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        
        AppointmentInfoDTO dto = new AppointmentInfoDTO(
            date, time, "Коваленко", "Олег", "Іванович", "38050",
            "Черкаси", "Шевченка", "12", "5", "Барсік", "кіт",
            "Петренко І.В.", 5, "Огляд", true
        );

        assertEquals("Барсік", dto.getPetName());
        assertEquals("Коваленко", dto.getOwnerLastName());
        assertEquals(5, dto.getVetExperience());
        assertTrue(dto.isVaccinated());
        
        assertTrue(dto.toString().contains("Барсік"));
    }
}