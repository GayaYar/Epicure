package com.moveo.epicure.entity;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
public class Meal {
    @Id
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
    @URL
    private String img;
    @NotNull
    private String category;
    @OneToMany(fetch = FetchType.LAZY)
    private List<Choice> choices;

    public Meal(Integer id) {
        this.id = id;
    }
}
