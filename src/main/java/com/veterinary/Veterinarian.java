package com.veterinary;

import javax.persistence.*;

@Entity
@Table(name = "veterinarian")
public class Veterinarian {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String fullName;
    private int experienceYears;

    public Veterinarian() {}

    public Veterinarian(String fullName, int experienceYears) {
        this.fullName = fullName;
        this.experienceYears = experienceYears;
    }

    public String getFullName() { return fullName; }
    public int getExperienceYears() { return experienceYears; }

    // Сетери, які виправляють помилки в тесті:
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setExperienceYears(int experienceYears) { this.experienceYears = experienceYears; }
}