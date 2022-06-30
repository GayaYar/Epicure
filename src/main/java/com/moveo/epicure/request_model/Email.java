package com.moveo.epicure.request_model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Email {
    private String subject;
    private String body;
}
