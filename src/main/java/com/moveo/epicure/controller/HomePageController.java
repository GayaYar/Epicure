package com.moveo.epicure.controller;

import com.moveo.epicure.dto.HomeInfo;
import com.moveo.epicure.service.HomePageService;
import javax.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("home")
public class HomePageController {
    private HomePageService service;

    @GetMapping
    public ResponseEntity<HomeInfo> getInfo(@RequestParam @Min(1) int weeklyChefId) {
        return ResponseEntity.ok(service.getHomeInfo(weeklyChefId));
    }
}
