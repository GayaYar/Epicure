package com.moveo.epicure.swagger.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChefBriefDTO {
    @NotNull
    @Min(1)
    private Integer id;
    @NotNull
    private String name;
    @NotNull
    @URL
    private String img;
}
