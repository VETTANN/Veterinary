package com.veterinary;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "owner")
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "owner_id")
    private Integer ownerId;

    @Column(name = "last_name") 
    private String lastName;

    @Column(name = "first_name") 
    private String firstName;

    @Column(name = "middle_name") 
    private String middleName;

    @Column(name = "city") 
    private String city;

    @Column(name = "street") 
    private String street;

    @Column(name = "house") 
    private String house;

    @Column(name = "apartment") 
    private String apartment;

    @Column(name = "phone", unique = true) 
    private String phone;

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

    public Integer getOwnerId() { 
        return ownerId; 
    }

    public String getLastName() { 
        return lastName; 
    }

    public String getFirstName() { 
        return firstName; 
    }

    public String getMiddleName() { 
        return middleName; 
    }

    public String getCity() { 
        return city; 
    }

    public String getStreet() { 
        return street; 
    }

    public String getHouse() { 
        return house; 
    }

    public String getApartment() { 
        return apartment; 
    }

    public String getPhone() { 
        return phone; 
    }

    public void setOwnerId(Integer ownerId) { 
        this.ownerId = ownerId; 
    }

    public void setLastName(String lastName) { 
        this.lastName = lastName; 
    }

    public void setFirstName(String firstName) { 
        this.firstName = firstName; 
    }

    public void setMiddleName(String middleName) { 
        this.middleName = middleName; 
    }
    
    public void setCity(String city) { 
        this.city = city; 
    }

    public void setStreet(String street) { 
        this.street = street; 
    }

    public void setHouse(String house) { 
        this.house = house; 
    }

    public void setApartment(String apartment) { 
        this.apartment = apartment; 
    }

    public void setPhone(String phone) { 
        this.phone = phone; 
    }

    @Override
    public String toString() {
        return lastName + " " + firstName + " " + middleName;
    }
}