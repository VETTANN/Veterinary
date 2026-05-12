package com.veterinary;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentInfoDTO {
    private LocalDate date;
    private LocalTime time;
    private String lastName;
    private String firstName;
    private String middleName;
    private String phone;
    private String city;
    private String street;
    private String house;
    private String apartment;
    private String petName;
    private String species;
    private String vetName;
    private Integer vetExp;
    private String diagnosis;
    private Boolean isVaccinated;

    // Конструктор повинен приймати ті ж типи, що й поля вище
    public AppointmentInfoDTO(LocalDate date, LocalTime time, String lastName, 
                              String firstName, String middleName, String phone, 
                              String city, String street, String house, 
                              String apartment, String petName, String species, 
                              String vetName, Integer vetExp, String diagnosis, 
                              Boolean isVaccinated) {
        this.date = date;
        this.time = time;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.phone = phone;
        this.city = city;
        this.street = street;
        this.house = house;
        this.apartment = apartment;
        this.petName = petName;
        this.species = species;
        this.vetName = vetName;
        this.vetExp = vetExp;
        this.diagnosis = diagnosis;
        this.isVaccinated = isVaccinated;
    }

    private String getOwnerFullName() {
        return lastName + " " + firstName + " " + middleName;
    }

    private String getPetFull() {
        return petName + " (" + species + ")";
    }

    private String getAddressFull() {
        String apt = (apartment != null) ? ", " + apartment : "";
        String addr = city + ", " + street + ", " + house + apt;
        return addr.length() > 30 ? addr.substring(0, 27) + "..." : addr;
    }

    @Override
    public String toString() {
        String diag = diagnosis.length() > 30 
                ? diagnosis.substring(0, 27) + "..." : diagnosis;
        String owner = getOwnerFullName().length() > 25 
                ? getOwnerFullName().substring(0, 22) + "..." : getOwnerFullName();
        
        String format = "| %-25s | %-18s | %-25s | %-12s | %-8s | %-30s | %-30s | %-10s | %-5s | %-15s |";
        
        return String.format(format,
                owner,
                getPetFull(),
                vetName,
                date,
                time,
                diag,
                getAddressFull(),
                (isVaccinated ? "Так" : "Ні"),
                vetExp,
                phone
        );
    }
}