package com.moveo.epicure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDetail {
    private Integer id;
}
