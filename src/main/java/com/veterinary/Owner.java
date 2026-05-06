package com.veterinary;

import javax.persistence.*;

@Entity
@Table(name = "owner")
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "owner_id")
    private Integer ownerId;

    // ПІБ (Окремі колонки для бази)
    @Column(name = "last_name") private String lastName;
    @Column(name = "first_name") private String firstName;
    @Column(name = "middle_name") private String middleName;

    // Адреса (Окремі колонки для бази)
    @Column(name = "city") private String city;
    @Column(name = "street") private String street;
    @Column(name = "house") private String house;
    @Column(name = "apartment") private String apartment;

    @Column(name = "phone", unique = true) private String phone;

    public Owner() {}

    public Owner(String lastName, String firstName, String middleName, 
                 String city, String street, String house, String apartment, String phone) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.city = city;
        this.street = street;
        this.house = house;
        this.apartment = apartment;
        this.phone = phone;
    }
}