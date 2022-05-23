package com.moveo.epicure.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Meal {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotNull
    private String name;
    private String description;
    private boolean spicy;
    private boolean vegan;
    private boolean glutenFree;
    @NotNull
    @Min(0)
    private double price;
    @NotNull
    private String img;
    @NotNull
    private String category;

}
