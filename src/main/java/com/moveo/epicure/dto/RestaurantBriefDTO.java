package com.moveo.epicure.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RestaurantBriefDTO {
    @NotNull
    private Integer id;
    @NotNull
    private String name;
    @NotNull
    private String chefName;
    @NotNull
    @Min(0) @Max(5)
    private int rating;
    @NotNull
    private String img;
}
