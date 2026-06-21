package com.veterinary;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    @Test
    void testComprehensiveDomainAndDtoCoverage() {
        Pet pet = new Pet();
        pet.setName("Мурчик");
        pet.setSpecies("кіт");
        assertEquals("Мурчик", pet.getName());
        assertEquals("кіт", pet.getSpecies());

        Veterinarian vet = new Veterinarian();
        vet.setFullName("Петренко Ірина Василівна");
        vet.setExperienceYears(5);
        assertEquals("Петренко Ірина Василівна", vet.getFullName());
        assertEquals(5, vet.getExperienceYears());

        Veterinarian vetWithArgs = new Veterinarian("Мельник Оксана Петрівна", 7);
        assertEquals("Мельник Оксана Петрівна", vetWithArgs.getFullName());
        assertEquals(7, vetWithArgs.getExperienceYears());

        Owner owner = new Owner();
        owner.setLastName("Коваленко");
        owner.setFirstName("Олег");
        owner.setPhone("380501234567");
        
        assertEquals("Коваленко", owner.getLastName());
        assertEquals("Олег", owner.getFirstName());
        assertEquals("380501234567", owner.getPhone());

        Appointment app = new Appointment();
        LocalDate date = LocalDate.of(2026, 6, 21);
        LocalTime time = LocalTime.of(10, 0);
        
        app.setPet(pet);
        app.setVeterinarian(vet);
        app.setAppointmentDate(date);
        app.setAppointmentTime(time);
        app.setDiagnosisTreatment("Огляд");
        app.setVaccinated(true);

        assertEquals(pet, app.getPet());
        assertEquals(vet, app.getVeterinarian());
        assertEquals(date, app.getAppointmentDate());
        assertEquals(time, app.getAppointmentTime());
        assertEquals("Огляд", app.getDiagnosisTreatment());
        assertTrue(app.isVaccinated());
        Appointment appWithArgs = new Appointment(pet, vet, date, time, "Лікування", false);
        assertFalse(appWithArgs.isVaccinated());

        AppointmentInfoDTO dto = new AppointmentInfoDTO(
            date, 
            time, 
            "Коваленко", "Олег", "Іванович", "380501234567",
            "м. Черкаси", "вул. Шевченка", "12", "кв.5",
            "Мурчик", "кіт",
            "Петренко Ірина Василівна", 5,
            "Огляд", 
            true
        );

        assertNotNull(dto);
        assertEquals("Мурчик", dto.getPetName());
        assertEquals("Коваленко", dto.getOwnerLastName());
        assertEquals("Петренко Ірина Василівна", dto.getVetFullName());
        assertTrue(dto.isVaccinated());
        assertEquals(5, dto.getVetExperience());
    }
}