package ru.stqa.pft.mantis.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table (name = "mantis_user_table")
public class UserData {

    @Id
    private int id = Integer.MAX_VALUE;
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

    public UserData withId(int id) {
        this.id = id;
        return this;
    }

    public UserData withName(String userName) {
        this.userName = userName;
        return this;
    }

    public UserData withEmail(String email) {
        this.email = email;
        return this;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserData userData = (UserData) o;
        return id == userData.id && Objects.equals(userName, userData.userName) && Objects.equals(email, userData.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, email);
    }
}
