package com.moveo.epicure.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne
    private Restaurant restaurant;
    @NotNull
    private boolean current;
    @NotNull
    private String comment;
    @NotNull
    @Min(0)
    private double overallPrice;
    @OneToOne
    @NotNull
    private Customer customer;
    @OneToMany(fetch = FetchType.LAZY)
    private List<ChosenMeal> chosenMeals;

    public Cart(boolean current, Customer customer) {
        this.current = current;
        this.comment = "";
        this.overallPrice = 0;
        this.customer = customer;
        this.chosenMeals = new ArrayList<>();
    }

    public Cart(Integer id) {
        this.id = id;
    }
}
