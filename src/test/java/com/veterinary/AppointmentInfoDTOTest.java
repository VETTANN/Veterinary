package com.veterinary;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class AppointmentInfoDTOTest {
    @Test
    void testMultipleAppointmentsDTO() {
        List<AppointmentInfoDTO> reportList = new ArrayList<>();

        AppointmentInfoDTO first = new AppointmentInfoDTO(
            LocalDate.of(2024, 9, 14), LocalTime.of(10, 0),
            "Коваленко", "Олег", "Іванович", "380501234567",
            "м. Київ", "вул. Шевченка", "12", "кв.5",
            "Барсик", "кіт",
            "Петренко Ірина Василівна", 5,
            "Щеплення комплекс.", true
        );

        AppointmentInfoDTO second = new AppointmentInfoDTO(
            LocalDate.of(2024, 9, 15), LocalTime.of(12, 30),
            "Сидоренко", "Петро", "Степанович", "380679876543",
            "м. Черкаси", "вул. Смілянська", "45", "кв.12",
            "Рекс", "собака",
            "Петренко Ірина Василівна", 5,
            "Огляд", false
        );

        reportList.add(first);
        reportList.add(second);

        assertEquals(2, reportList.size());
        assertEquals("Барсик", reportList.get(0).getPetName());
        assertEquals("Сидоренко", reportList.get(1).getOwnerLastName());
        assertEquals("кіт", reportList.get(0).getPetSpecies());
        assertFalse(reportList.get(1).isVaccinated());
    }
}