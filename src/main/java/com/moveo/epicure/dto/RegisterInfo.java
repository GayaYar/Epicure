package com.moveo.epicure.dto;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterInfo {
    @NotNull
    private LoginInfo loginInfo;
    @NotNull
    private String name;
}