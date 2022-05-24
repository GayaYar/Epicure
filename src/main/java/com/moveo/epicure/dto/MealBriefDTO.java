package com.moveo.epicure.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MealBriefDTO {
    @NotNull
    @Min(1)
    private Integer id;
    @NotNull
    private String name;
    private String description;
    @NotNull
    @Min(0)
    private double price;
    private String img;
}
