
package com.imaginationHoldings.domain;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Objects;

public class Person {
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    private int id;
    private int passportID;
    private LocalDate birthDate;

    public Person(String firstName, String lastName, String gender, int id, String birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.id = id;
        this.birthDate = resolveBirthDate(birthDate);
        this.age = calculateAge();
        this.passportID = id;
    }


    public Person(String firstName, String lastName, String gender, int passportID, LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.passportID = passportID;
        this.id = passportID;
        this.birthDate = birthDate;
        this.age = calculateAge();
    }

    public Person(String firstName, String lastName, String gender, int age, int id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.birthDate = resolveDateByAge(age);
        this.gender = gender;
        this.id = id;
        this.passportID = id;
    }

    private int calculateAge() {
        return Period.between(this.birthDate, LocalDate.now()).getYears();
    }

    private LocalDate resolveBirthDate(String birthDate) {
        DateTimeFormatter[] formatos = {
                DateTimeFormatter.ofPattern("dd/MM/yyyy"),
                DateTimeFormatter.ofPattern("dd/M/yyyy"),
                DateTimeFormatter.ofPattern("d/MM/yyyy"),
                DateTimeFormatter.ofPattern("dd/MM/yy"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd")
        };

        for (DateTimeFormatter formato : formatos) {
            try {
                return LocalDate.parse(birthDate, formato);
            } catch (DateTimeParseException e) {
                // continue trying
            }
        }
        throw new IllegalArgumentException("Formato de fecha no válido: " + birthDate);
    }

    private LocalDate resolveDateByAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("La edad no puede ser negativa");
        }
        int currentYear = LocalDate.now().getYear();
        int birthYear = currentYear - age;
        return LocalDate.of(birthYear, 1, 1);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
        this.birthDate = resolveDateByAge(age);
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPassportID() {
        return passportID;
    }

    public void setPassportID(int passportID) {
        this.passportID = passportID;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = resolveBirthDate(birthDate);
        this.age = calculateAge();
    }

    public String getFormattedBirthDate() {
        return birthDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    @Override
    public String toString() {
        return firstName + ',' + lastName + ',' + gender + ',' + id + (passportID != id ? passportID + "," : ",") + getFormattedBirthDate() + ",(" + age + ");";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person person)) return false;
        return id == person.id || passportID == person.passportID;
    }
}
