package com.moveo.epicure.dto;

import java.util.List;
import lombok.Data;

@Data
public class ChoiceDTO {

    private String title;
    private List<String> options;
    private Integer minChoices;
    private Integer maxChoices;
}
