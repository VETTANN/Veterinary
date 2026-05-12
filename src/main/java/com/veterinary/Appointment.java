package com.veterinary;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "vet_id")
    private Veterinarian veterinarian;

    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String diagnosisTreatment;
    private boolean isVaccinated;

    public Appointment() {}

    public Appointment(Pet pet, Veterinarian veterinarian, LocalDate appointmentDate, 
                       LocalTime appointmentTime, String diagnosisTreatment, boolean isVaccinated) {
        this.pet = pet;
        this.veterinarian = veterinarian;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.diagnosisTreatment = diagnosisTreatment;
        this.isVaccinated = isVaccinated;
    }

    // --- Геттери ---
    public Integer getId() { return id; }
    public Pet getPet() { return pet; }
    public Veterinarian getVeterinarian() { return veterinarian; }
    public LocalDate getAppointmentDate() { return appointmentDate; }
    public LocalTime getAppointmentTime() { return appointmentTime; }
    public String getDiagnosisTreatment() { return diagnosisTreatment; }
    public boolean isVaccinated() { return isVaccinated; }

    public void setId(Integer id) { this.id = id; }
    
    public void setPet(Pet pet) { this.pet = pet; }
    
    public void setVeterinarian(Veterinarian veterinarian) { this.veterinarian = veterinarian; }
    
    public void setAppointmentDate(LocalDate appointmentDate) { this.appointmentDate = appointmentDate; }
    
    public void setAppointmentTime(LocalTime appointmentTime) { this.appointmentTime = appointmentTime; }
    
    public void setDiagnosisTreatment(String diagnosisTreatment) { this.diagnosisTreatment = diagnosisTreatment; }
    
    public void setVaccinated(boolean vaccinated) { this.isVaccinated = vaccinated; }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", pet=" + (pet != null ? pet.getName() : "null") +
                ", date=" + appointmentDate +
                ", time=" + appointmentTime +
                ", diagnosis='" + diagnosisTreatment + '\'' +
                ", vaccinated=" + isVaccinated +
                '}';
    }
}