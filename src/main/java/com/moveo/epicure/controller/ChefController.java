package com.moveo.epicure.controller;

import com.moveo.epicure.swagger.dto.ChefBriefDTO;
import com.moveo.epicure.swagger.dto.ChefDTO;
import com.moveo.epicure.exception.NotFoundException;
import com.moveo.epicure.service.ChefService;
import java.util.List;
import java.util.Optional;
import javax.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("chefs")
public class ChefController {
    @Autowired
    private ChefService service;

    @GetMapping("/id")
    public ResponseEntity<ChefDTO> getById(@RequestParam @Min(1) Integer id) {
        Optional<ChefDTO> optionalChefDTO = service.findChefById(id);
        if(optionalChefDTO.isEmpty()) {
            throw new NotFoundException("chef");
        }
        return ResponseEntity.ok(optionalChefDTO.get());
    }

    @GetMapping
    public ResponseEntity<List<ChefBriefDTO>> getChefs(@RequestParam(required = false) Boolean newest) {
        return ResponseEntity.ok(service.getChefsByOrder(newest));
    }

    @PutMapping("/views")
    public ResponseEntity<Void> addViews(@RequestParam Integer chefId, @RequestParam @Min(1) int views) {
        service.addViews(chefId, views);
        return ResponseEntity.ok().build();
    }
}
