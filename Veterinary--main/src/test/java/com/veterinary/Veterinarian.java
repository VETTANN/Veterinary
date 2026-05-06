package com.veterinary;

import javax.persistence.*;

@Entity
@Table(name = "veterinarian")
public class Veterinarian {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vet_id")
    private Integer vetId;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "experience_years")
    private Integer experienceYears;

    public Veterinarian() {}

    public Veterinarian(String fullName, Integer experienceYears) {
        this.fullName = fullName;
        this.experienceYears = experienceYears;
    }

    public String getFullName() { return fullName; }
    public Integer getExperienceYears() { return experienceYears; }
}
