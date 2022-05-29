package com.moveo.epicure.service;

import com.moveo.epicure.dto.ChefBriefDTO;
import com.moveo.epicure.dto.ChefDTO;
import com.moveo.epicure.entity.Chef;
import com.moveo.epicure.repo.ChefRepo;
import com.moveo.epicure.util.DtoMapper;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ChefService {
    @Autowired
    private ChefRepo chefRepo;

    public Optional<ChefDTO> findChefById(Integer id) {
        Optional<Chef> optionalChef = chefRepo.findByIdWithRestaurants(id);
        if(optionalChef.isPresent()) {
            Chef chef = optionalChef.get();
            return Optional.of(DtoMapper.chefToDto(chef));
        }
        return Optional.empty();
    }

    public List<ChefBriefDTO> getChefsByParams(boolean newest, boolean viewed) {
        return null;
    }
}
