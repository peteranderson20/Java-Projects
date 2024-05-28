package edu.miracosta.cs112.capstone.Model;

import java.util.Objects;

public class Admin extends User implements Comparable<User> {

    private static final long serialVersionUID = 3373906356813115975L;

    private String adminLevel;

    public Admin(String firstName, String lastName, String username, String password, String adminLevel) {
        super(firstName, lastName, username, password);
        this.adminLevel = adminLevel;
    }

    public String getAdminLevel() {
        return adminLevel;
    }

    public boolean canAddUser() {
        return "admin".equals(adminLevel);
    }

    public boolean canDeleteUser() {
        return "admin".equals(adminLevel) || "moderator".equals(adminLevel);
    }

    public boolean canBanUser() {
        return "admin".equals(adminLevel) || "moderator".equals(adminLevel);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Admin admin = (Admin) o;
        return Objects.equals(adminLevel, admin.adminLevel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), adminLevel);
    }

    @Override
    public String toString() {
        return super.toString() + ", Admin [adminLevel='" + adminLevel + "']";
    }

    @Override
    public int compareTo(User o) {
        if (o instanceof Admin) {
            Admin otherAdmin = (Admin) o;
            int result = super.compareTo(otherAdmin);
            if (result != 0) {
                return result;
            }
            return this.adminLevel.compareTo(otherAdmin.adminLevel);
        } else {
            return super.compareTo(o);
        }
    }
}
