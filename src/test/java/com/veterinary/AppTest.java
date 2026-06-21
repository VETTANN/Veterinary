package com.veterinary;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    @Test
    void testComprehensiveCoverage() {
        List<AppointmentInfoDTO> appointments = new ArrayList<>();

        appointments.add(new AppointmentInfoDTO(
            LocalDate.of(2024, 9, 14), LocalTime.of(10, 0),
            "Коваленко", "Олег", "Іванович", "380501234567",
            "м. Київ", "вул. Шевченка", "12", "кв.5",
            "Барсік", "кіт",
            "Петренко Ірина Василівна", 5,
            "Щеплення комплексною вакциною", true
        ));

        appointments.add(new AppointmentInfoDTO(
            LocalDate.of(2024, 9, 14), LocalTime.of(14, 20),
            "Бондаренко", "Дмитро", "Сергійович", "380731234567",
            "м. Київ", "просп. Перемоги", "33", "",
            "Муся", "кішка",
            "Іваненко Андрій Миколайович", 3,
            "Огляд після операції", false
        ));

        appointments.add(new AppointmentInfoDTO(
            LocalDate.of(2024, 9, 15), LocalTime.of(9, 15),
            "Кравченко", "Сергій", "Володимирович", "380631234567",
            "м. Київ", "вул. Саксаганського", "7", "",
            "Зірка", "кішка",
            "Мельник Оксана Петрівна", 7,
            "Лікування захворювання шкіри", false
        ));

        assertEquals(3, appointments.size());
        
        long catCount = appointments.stream()
            .filter(a -> a.getPetSpecies().toLowerCase().contains("кіт") || a.getPetSpecies().toLowerCase().contains("кішк"))
            .count();
        assertEquals(3, catCount);

        long experienceMoreThanFour = appointments.stream()
            .filter(a -> a.getVetExperience() > 4)
            .count();
        assertEquals(2, experienceMoreThanFour);

        long vaccinatedCount = appointments.stream()
            .filter(AppointmentInfoDTO::isVaccinated)
            .count();
        assertEquals(1, vaccinatedCount);
    }
}