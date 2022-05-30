package com.moveo.epicure.dto;


import com.moveo.epicure.entity.Label;
import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class MealDTO {
    @NotNull
    @Min(1)
    private Integer id;
    @NotNull
    private String name;
    private String description;
    private List<Label> labels;
    @NotNull
    @Min(0)
    private double price;
    private List<ChoiceDTO> choices;
    @NotNull
    private String img;
    @NotNull
    @Min(1)
    private int quantity;
}
