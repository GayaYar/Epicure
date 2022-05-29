package com.moveo.epicure.dto;

import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
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
