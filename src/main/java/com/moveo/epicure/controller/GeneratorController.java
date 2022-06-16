package com.moveo.epicure.controller;

import com.moveo.epicure.generated.GeneratedUser;
import com.moveo.epicure.generated.Name;
import com.moveo.epicure.generated.UserList;
import com.moveo.epicure.service.GeneratorService;
import java.util.Comparator;
import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("generator")
public class GeneratorController {
    private GeneratorService service;

    public GeneratorController(GeneratorService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<GeneratedUser>> generate(@RequestParam @Min(1) @Max(15) int amount) {
        return ResponseEntity.ok(service.generatedUsers(amount));
    }
}
