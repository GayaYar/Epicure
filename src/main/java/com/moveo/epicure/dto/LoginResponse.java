package com.moveo.epicure.dto;

import lombok.Data;

@Data
public class LoginResponse {
    private String name;
    private String jwtToken;
}
