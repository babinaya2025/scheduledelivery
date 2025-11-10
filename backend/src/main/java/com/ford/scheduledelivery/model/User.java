// src/main/java/com/ford/scheduledelivery/model/User.java
package com.ford.scheduledelivery.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.Objects; // For equals and hashCode

@Entity
@Table(name = "app_user")
public class User {
    @Id
    private Integer userId;
    private String firstName;
    private String lastName;
    private String email;
    private Integer departmentId;
    private String role;
    private Integer regionId;
    private String managerName;
    private String managerEmailId;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PO> poOrders;

    // @NoArgsConstructor
    public User() {
    }

    // Manually defined constructor (replaces @AllArgsConstructor(exclude = "poOrders"))
    public User(Integer userId, String firstName, String lastName, String email,
                Integer departmentId, String role, Integer regionId,
                String managerName, String managerEmailId) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.departmentId = departmentId;
        this.role = role;
        this.regionId = regionId;
        this.managerName = managerName;
        this.managerEmailId = managerEmailId;
    }

    // @Getter
    public Integer getUserId() {
        return userId;
    }

    // @Setter
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    // @Getter
    public String getFirstName() {
        return firstName;
    }

    // @Setter
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // @Getter
    public String getLastName() {
        return lastName;
    }

    // @Setter
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // @Getter
    public String getEmail() {
        return email;
    }

    // @Setter
    public void setEmail(String email) {
        this.email = email;
    }

    // @Getter
    public Integer getDepartmentId() {
        return departmentId;
    }

    // @Setter
    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    // @Getter
    public String getRole() {
        return role;
    }

    // @Setter
    public void setRole(String role) {
        this.role = role;
    }

    // @Getter
    public Integer getRegionId() {
        return regionId;
    }

    // @Setter
    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    // @Getter
    public String getManagerName() {
        return managerName;
    }

    // @Setter
    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    // @Getter
    public String getManagerEmailId() {
        return managerEmailId;
    }

    // @Setter
    public void setManagerEmailId(String managerEmailId) {
        this.managerEmailId = managerEmailId;
    }

    // @Getter
    public List<PO> getPoOrders() {
        return poOrders;
    }

    // @Setter
    public void setPoOrders(List<PO> poOrders) {
        this.poOrders = poOrders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", departmentId=" + departmentId +
                ", role='" + role + '\'' +
                ", regionId=" + regionId +
                ", managerName='" + managerName + '\'' +
                ", managerEmailId='" + managerEmailId + '\'' +
                '}';
    }
}
