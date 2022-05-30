package com.moveo.epicure.dto;

import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
