package com.moveo.epicure.entity;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChosenMeal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotNull
    @ManyToOne
    private Meal meal;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Option> chosenOptions;
    @NotNull
    @URL
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
    @NotNull
    @ManyToOne
    private Cart cart;

    public ChosenMeal(List<Option> chosenOptions, String img, double mealPrice, int amount, double finalPrice
            , Meal meal, Cart cart) {
        this.chosenOptions = chosenOptions;
        this.img = img;
        this.mealPrice = mealPrice;
        this.amount = amount;
        this.finalPrice = finalPrice;
        this.meal = meal;
        this.cart = cart;
    }
}
