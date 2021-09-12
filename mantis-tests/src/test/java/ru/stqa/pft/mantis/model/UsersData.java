package ru.stqa.pft.mantis.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table (name = "mantis_user_table")
public class UsersData {

    @Id
    private int id = Integer.MAX_VALUE;

    @Column(name = "username")
    private String userName;

    private String email;

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public UsersData withId(int id) {
        this.id = id;
        return this;
    }

    public UsersData withName(String userName) {
        this.userName = userName;
        return this;
    }

    public UsersData withEmail(String email) {
        this.email = email;
        return this;
    }

    @Override
    public String toString() {
        return "UsersData{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersData usersData = (UsersData) o;
        return id == usersData.id && Objects.equals(userName, usersData.userName) && Objects.equals(email, usersData.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, email);
    }
}
