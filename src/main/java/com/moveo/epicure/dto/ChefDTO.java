package com.moveo.epicure.dto;

import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChefDTO {
    @NotNull
    private String name;
    @NotNull
    private String description;
    @NotNull
    private List<RestaurantBriefDTO> restaurants;
    @NotNull
    @URL
    private String img;
}
