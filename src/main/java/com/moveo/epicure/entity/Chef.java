package com.moveo.epicure.entity;

import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Entity
@Data
@NoArgsConstructor
public class Chef {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotNull
    private String name;
    @NotNull
    private String description;
    @NotNull
    @URL
    private String img;
    @OneToMany(fetch = FetchType.LAZY)
    private List<Restaurant> restaurants;
    @NotNull
    @Min(0)
    private int views;
    @NotNull
    private Date addingDate;

    public void addViews(int amount) {
        views+=amount;
    }

}
