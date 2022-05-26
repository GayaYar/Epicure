package com.moveo.epicure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
@Data
@AllArgsConstructor
public class CustomerDetail {
    private Integer id;
}
