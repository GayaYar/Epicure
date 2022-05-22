package com.moveo.epicure.dto;


import com.moveo.epicure.model.Label;
import java.util.List;
import lombok.Data;

@Data
public class FullMealDTO {
    private Integer id;
    private String description;
    private List<Label> labels;
    private double price;
    private List<ChoiceDTO> choices;
}
