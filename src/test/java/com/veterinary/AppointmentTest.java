package com.veterinary;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.*;

class AppointmentTest {

    @Test
    void testAppointmentFullLifecycle() {
        Pet pet = new Pet();
        pet.setName("Барсік");
        pet.setSpecies("кіт");

        Veterinarian vet = new Veterinarian("Петренко Ірина Василівна", 5);

        LocalDate date = LocalDate.of(2026, 6, 21);
        LocalTime time = LocalTime.of(10, 0);

        Appointment appointment = new Appointment(pet, vet, date, time, "Вакцинація сказу", true);

        assertEquals(pet, appointment.getPet());
        assertEquals(vet, appointment.getVeterinarian());
        assertEquals(date, appointment.getAppointmentDate());
        assertEquals(time, appointment.getAppointmentTime());
        assertEquals("Вакцинація сказу", appointment.getDiagnosisTreatment());
        assertTrue(appointment.isVaccinated());

        Appointment emptyApp = new Appointment();
        emptyApp.setId(42);
        emptyApp.setDiagnosisTreatment("Огляд");
        emptyApp.setVaccinated(false);

        assertEquals(42, emptyApp.getId());
        assertEquals("Огляд", emptyApp.getDiagnosisTreatment());
        assertFalse(emptyApp.isVaccinated());
    }
}