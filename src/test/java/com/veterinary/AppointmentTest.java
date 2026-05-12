package com.veterinary;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

class AppointmentTest {
    @Test
    void testAppointmentGettersAndSetters() {
        Owner owner = new Owner("Коваленко", "Олег", "Іванович", "Черкаси", "Шевченка", "12", "5", "38050");
        Pet pet = new Pet(owner, "Барсік", "кіт");
        Veterinarian vet = new Veterinarian("Мельник Оксана", 7);
        
        // Створюємо прийом
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        Appointment app = new Appointment(pet, vet, date, time, "Щеплення", true);

        // Перевіряємо геттери
        assertEquals(pet, app.getPet());
        assertEquals(vet, app.getVeterinarian());
        assertEquals(date, app.getAppointmentDate());
        assertEquals(time, app.getAppointmentTime());
        assertEquals("Щеплення", app.getDiagnosisTreatment());
        assertTrue(app.isVaccinated());
    }
}