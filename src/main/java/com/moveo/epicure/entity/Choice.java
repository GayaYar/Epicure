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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Choice {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Min(0)
    private Integer minChoices;
    @Min(1)
    private Integer maxChoices;
    @ManyToOne
    @NotNull
    private Meal meal;
    @OneToMany(fetch = FetchType.EAGER)
    @NotNull
    private List<Option> options;

}
