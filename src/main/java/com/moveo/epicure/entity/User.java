package com.moveo.epicure.entity;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public abstract class User {
    @NotNull
    @Pattern(regexp = "^(.+)@(\\S+)$", message = "invalid email address")
    @Column(unique = true)
    protected String email;
    @NotNull
    @Size(min = 4)
    protected String password;
}
