package com.imaginationHoldings.domain;

import java.io.Serializable;
import java.util.Objects;

public class Company implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private String country;
    private String contactEmail;

    public Company(int id, String name, String country, String contactEmail) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.contactEmail = contactEmail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Company company)) return false;
        return id == company.id && Objects.equals(name, company.name);
    }
}
