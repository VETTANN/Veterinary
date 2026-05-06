package com.veterinary;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private Integer appointmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vet_id", nullable = false)
    private Veterinarian veterinarian;

    @Column(name = "appointment_date", nullable = false)
    private LocalDate appointmentDate;

    @Column(name = "appointment_time", nullable = false)
    private LocalTime appointmentTime;

    @Column(name = "diagnosis_treatment", nullable = false)
    private String diagnosisTreatment;

    @Column(name = "is_vaccinated", nullable = false)
    private Boolean isVaccinated;

    public Appointment() {}

    public Appointment(Pet pet, Veterinarian veterinarian, LocalDate appointmentDate, LocalTime appointmentTime, String diagnosisTreatment, Boolean isVaccinated) {
        this.pet = pet;
        this.veterinarian = veterinarian;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.diagnosisTreatment = diagnosisTreatment;
        this.isVaccinated = isVaccinated;
    }
}
