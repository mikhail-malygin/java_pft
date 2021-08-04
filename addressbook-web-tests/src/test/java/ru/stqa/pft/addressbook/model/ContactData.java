package ru.stqa.pft.addressbook.model;

import java.util.Objects;

public class ContactData {
    private int id;
    private final String firstName;
    private final String middleName;
    private final String lastName;
    private final String number;
    private final String email;
    private String group;

    public ContactData(int id, String firstName, String middleName, String lastName, String number, String email, String group) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.number = number;
        this.email = email;
        this.group = group;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ContactData(String firstName, String middleName, String lastName, String number, String email, String group) {
        this.id = Integer.MAX_VALUE;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.number = number;
        this.email = email;
        this.group = group;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNumber() {
        return number;
    }

    public String getEmail() {
        return email;
    }

    public String getGroup() {
        return group;
    }

    @Override
    public String toString() {
        return "ContactData{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactData that = (ContactData) o;
        return Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }
}
