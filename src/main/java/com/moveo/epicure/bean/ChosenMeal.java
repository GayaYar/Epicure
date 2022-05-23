package com.moveo.epicure.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class ChosenMeal {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotNull
    @ManyToOne
    private Meal meal;
    @NotNull
    @ManyToOne
    private Cart cart;
    @NotNull
    private String img;
    @NotNull
    @Min(0)
    private double mealPrice;
    @NotNull
    @Min(1)
    private int amount;
    @NotNull
    @Min(0)
    private double finalPrice;

}
