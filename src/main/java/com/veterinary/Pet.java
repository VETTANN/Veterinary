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

    public Integer getPetId() { return petId; }
    public String getName() { return name; }
    public String getSpecies() { return species; }
    public Owner getOwner() { return owner; }
    public void setName(String name) { this.name = name; }
    public void setSpecies(String species) { this.species = species; }
    public void setOwner(Owner owner) { this.owner = owner; }

    @Override
public String toString() {
    return "Pet{" +
            "petId=" + petId +
            ", name='" + name + '\'' +
            ", species='" + species + '\'' +
            ", owner=" + owner + 
            '}';
}
}