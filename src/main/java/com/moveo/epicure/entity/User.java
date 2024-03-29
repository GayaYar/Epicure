package com.moveo.epicure.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotNull
    private String name;
    @NotNull
    @Pattern(regexp = "^(.+)@(\\S+)$", message = "invalid email address")
    private String email;
    @NotNull
    @Size(min = 4)
    private String password;
    @Enumerated(value = EnumType.STRING)
    private UserType userType;

    public User(Integer id) {
        this.id = id;
    }

    public User(String name, String email, String password, UserType userType) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        defaultType();
    }

    public UserType getUserType() {
        defaultType();
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
        defaultType();
    }

    private void defaultType() {
        if(userType==null) {
            userType = UserType.CUSTOMER;
        }
    }
}
