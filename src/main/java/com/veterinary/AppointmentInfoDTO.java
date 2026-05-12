package com.veterinary;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentInfoDTO {
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String ownerLastName;
    private String ownerFirstName;
    private String ownerMiddleName;
    private String ownerPhone;
    private String ownerCity;
    private String ownerStreet;
    private String ownerHouse;
    private String ownerApartment;
    private String petName;
    private String petSpecies;
    private String vetFullName;
    private int vetExperience;
    private String diagnosis;
    private boolean isVaccinated;

    public AppointmentInfoDTO(LocalDate appointmentDate, LocalTime appointmentTime,
                              String ownerLastName, String ownerFirstName, String ownerMiddleName, String ownerPhone,
                              String ownerCity, String ownerStreet, String ownerHouse, String ownerApartment,
                              String petName, String petSpecies, String vetFullName, int vetExperience,
                              String diagnosis, boolean isVaccinated) {
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.ownerLastName = ownerLastName;
        this.ownerFirstName = ownerFirstName;
        this.ownerMiddleName = ownerMiddleName;
        this.ownerPhone = ownerPhone;
        this.ownerCity = ownerCity;
        this.ownerStreet = ownerStreet;
        this.ownerHouse = ownerHouse;
        this.ownerApartment = ownerApartment;
        this.petName = petName;
        this.petSpecies = petSpecies;
        this.vetFullName = vetFullName;
        this.vetExperience = vetExperience;
        this.diagnosis = diagnosis;
        this.isVaccinated = isVaccinated;
    }
    
    public LocalDate getAppointmentDate() { return appointmentDate; }
    public LocalTime getAppointmentTime() { return appointmentTime; }
    public String getOwnerLastName() { return ownerLastName; }
    public String getOwnerFirstName() { return ownerFirstName; }
    public String getOwnerMiddleName() { return ownerMiddleName; }
    public String getOwnerPhone() { return ownerPhone; }
    
    public String getOwnerCity() { return ownerCity; }
    public String getCity() { return ownerCity; } // Аліас для тесту
    
    public String getOwnerStreet() { return ownerStreet; }
    public String getStreet() { return ownerStreet; } // Аліас для тесту
    
    public String getOwnerHouse() { return ownerHouse; }
    public String getHouse() { return ownerHouse; } // Аліас для тесту
    
    public String getOwnerApartment() { return ownerApartment; }
    public String getApartment() { return ownerApartment; } // Аліас для тесту

    public String getPetName() { return petName; }
    public String getPetSpecies() { return petSpecies; }
    public String getVetFullName() { return vetFullName; }
    public int getVetExperience() { return vetExperience; }
    public String getDiagnosis() { return diagnosis; }
    public boolean isVaccinated() { return isVaccinated; }

    @Override
    public String toString() {
        return String.format("| %-25s | %-15s | %-25s | %-12s | %-5s |",
                ownerLastName + " " + (ownerFirstName != null ? ownerFirstName.substring(0, 1) + "." : ""),
                petName + " (" + petSpecies + ")",
                vetFullName,
                appointmentDate,
                appointmentTime);
    }
}