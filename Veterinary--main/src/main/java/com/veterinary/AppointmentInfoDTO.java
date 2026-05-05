package com.veterinary;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentInfoDTO {
    private LocalDate date;
    private LocalTime time;
    private String lastName, firstName, middleName;
    private String phone;
    private String city, street, house, apartment;
    private String petName, species;
    private String vetName;
    private Integer vetExp;
    private String diagnosis;
    private Boolean isVaccinated;

    public AppointmentInfoDTO(LocalDate date, LocalTime time, String lastName, String firstName, String middleName, String phone, String city, String street, String house, String apartment, String petName, String species, String vetName, Integer vetExp, String diagnosis, Boolean isVaccinated) {
        this.date = date;
        this.time = time;
        this.lastName = lastName; this.firstName = firstName; this.middleName = middleName;
        this.phone = phone;
        this.city = city; this.street = street; this.house = house; this.apartment = apartment;
        this.petName = petName; this.species = species;
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
        String diagShort = diagnosis.length() > 30 ? diagnosis.substring(0, 27) + "..." : diagnosis;
        String ownerShort = getOwnerFullName().length() > 25 ? getOwnerFullName().substring(0, 22) + "..." : getOwnerFullName();
        
        return String.format("| %-25s | %-18s | %-25s | %-12s | %-8s | %-30s | %-30s | %-10s | %-5s | %-15s |",
                ownerShort,         // Власник тварини
                getPetFull(),       // Тварина та вид
                vetName,            // Ветеринар
                date,               // Дата прийому
                time,               // Час прийому
                diagShort,          // Діагноз та лікування
                getAddressFull(),   // Адреса власника
                (isVaccinated ? "Так" : "Ні"), // Вакцинація
                vetExp,             // Стаж
                phone               // Контактний телефон
        );
    }
}
