package com.moveo.epicure.dto;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterInfo {
    @NotNull
    private LoginInfo loginInfo;
    @NotNull
    private String name;
}
