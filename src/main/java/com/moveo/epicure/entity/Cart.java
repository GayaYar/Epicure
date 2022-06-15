package com.moveo.epicure.entity;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotNull
    private boolean current;
    @NotNull
    private String comment;
    @NotNull
    @Min(0)
    private double overallPrice;
    @OneToOne
    @NotNull
    private User customer;
    @OneToMany(fetch = FetchType.LAZY)
    private List<ChosenMeal> chosenMeals;

    public Cart(boolean current, User customer) {
        this.current = current;
        this.customer = customer;
        defaultValues();
    }

    public Cart(Integer id) {
        this.id = id;
    }

    public void defaultValues() {
        this.chosenMeals = null;
        this.comment = "";
        this.overallPrice = 0;
    }

}
