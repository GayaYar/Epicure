package com.moveo.epicure.swagger.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginInfo {
    @NotNull
    @Pattern(regexp = "^(.+)@(\\S+)$", message = "invalid email address")
    private String email;
    @NotNull
    @Size(min = 4)
    private String password;
}
