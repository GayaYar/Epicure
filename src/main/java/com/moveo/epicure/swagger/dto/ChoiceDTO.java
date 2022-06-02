package com.moveo.epicure.swagger.dto;

import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChoiceDTO {

    @NotNull
    private String title;
    @NotNull
    private List<OptionDTO> options;
    @Min(0)
    private Integer minChoices;
    @Min(1)
    private Integer maxChoices;
}
