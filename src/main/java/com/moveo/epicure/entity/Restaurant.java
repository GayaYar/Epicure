package com.moveo.epicure.entity;

import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotNull
    @Column(unique = true)
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    private Chef chef;
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
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date creation;
    @OneToMany(fetch = FetchType.LAZY)
    private List<Meal> meals;


    public Restaurant(String name, int rating, String img, boolean open, int popularity, double latitude,
            double longitude,
            int price, Date creation) {
        this.name = name;
        this.rating = rating;
        this.img = img;
        this.open = open;
        this.popularity = popularity;
        this.latitude = latitude;
        this.longitude = longitude;
        this.price = price;
        this.creation = creation;
    }
}
