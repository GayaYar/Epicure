package com.moveo.epicure.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
@AllArgsConstructor
public class AdminRestaurantDto {
    private Integer id;
    @NotNull
    private String name;
    @NotNull
    @Min(0) @Max(5)
    private int rating;
    @NotNull
    @URL
    private String img;
    @NotNull
    private boolean open;
    @NotNull
    private int popularity;
    @NotNull
    private double latitude;
    @NotNull
    private double longitude;
    @NotNull
    @Min(1) @Max(5)
    private int price;
}
