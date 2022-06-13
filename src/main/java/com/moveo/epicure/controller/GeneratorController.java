package com.moveo.epicure.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("generator")
public class GeneratorController {

    private RestTemplate restTemplate;
    private String userUrl;

    public GeneratorController() {
        restTemplate = new RestTemplate();
        userUrl = "https://randomuser.me/api/";
    }


}
