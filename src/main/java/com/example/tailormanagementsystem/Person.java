package com.example.tailormanagementsystem;

import java.io.Serializable;

public class Person implements Serializable {

    private String name;
    private String email;
    private Address address;

    public Person(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Person(String name, String email, Address address) {
        this.name = name;
        this.email = email;
        this.address = address;
    }
    public Person()
    {

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = (Address) address.clone();
    }

    @Override
    public String toString() {
        return  "name='" + name + '\'' +
                ", email='" + email + '\'' +
                "\n Address: " + address ;
    }
}
