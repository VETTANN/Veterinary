package com.veterinary;

import javax.persistence.*;

@Entity
@Table(name = "pet")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pet_id")
    private Integer petId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private Owner owner;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "species")
    private String species;

    public Pet() {}

    public Pet(Owner owner, String name, String species) {
        this.owner = owner;
        this.name = name;
        this.species = species;
    }

    public String getName() { return name; }
    public String getSpecies() { return species; }
    public Owner getOwner() { return owner; }
}
