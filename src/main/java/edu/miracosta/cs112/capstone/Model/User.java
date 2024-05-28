package edu.miracosta.cs112.capstone.Model;
import java.io.Serializable;
import java.util.*;

import org.mindrot.jbcrypt.BCrypt;

public class User implements Serializable, Comparable<User> {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private static final long serialVersionUID = 8253087509079461447L;

    public User(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(username, user.username) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, username, password);
    }


    public String toString() {
        return "User[" +
                "First name: " + firstName +
                ", Last name: " + lastName +
                ", Username: " + username +
                ", Password: " + hashPassword(password) +
                "]";
    }

    public static String hashPassword(String password) {
        String salt = BCrypt.gensalt();
        return BCrypt.hashpw(password, salt);
    }

    public static boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }

    @Override
    public int compareTo(User o) {
        int firstNameComp = this.firstName.compareTo(o.firstName);
        if (firstNameComp != 0) return firstNameComp;

        int lastNameComp = this.lastName.compareTo(o.lastName);
        if (lastNameComp != 0) return lastNameComp;

        int usernameComp = this.username.compareTo(o.username);
        if (usernameComp != 0) return usernameComp;

        return this.password.compareTo(o.password);
    }
}
