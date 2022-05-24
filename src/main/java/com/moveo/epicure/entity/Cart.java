package com.moveo.epicure.entity;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
    @ManyToOne
    @NotNull
    private Restaurant restaurant;
    @OneToMany(fetch = FetchType.EAGER)
    private List<ChosenMeal> chosenMeals;
    @NotNull
    private boolean current;
    @NotNull
    private String comment;
    @NotNull
    @Min(0)
    private double overallPrice;
}
