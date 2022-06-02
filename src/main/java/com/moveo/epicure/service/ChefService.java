package com.moveo.epicure.service;

import com.moveo.epicure.swagger.dto.ChefBriefDTO;
import com.moveo.epicure.swagger.dto.ChefDTO;
import com.moveo.epicure.entity.Chef;
import com.moveo.epicure.exception.NotFoundException;
import com.moveo.epicure.repo.ChefRepo;
import com.moveo.epicure.util.DtoMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ChefService {
    @Autowired
    private ChefRepo chefRepo;

    @Transactional(readOnly = true)
    public Optional<ChefDTO> findChefById(Integer id) {
        Optional<Chef> optionalChef = chefRepo.findByIdWithRestaurants(id);
        if(optionalChef.isPresent()) {
            return Optional.of(DtoMapper.chefToDto(optionalChef.get()));
        }
        return Optional.empty();
    }

    @Transactional(readOnly = true)
    public List<ChefBriefDTO> getChefsByOrder(Boolean newest) {
        List<Chef> chefs = (newest!=null && newest==true) ? chefRepo.findByOrderByAddingDateDescViewsDesc() :
                chefRepo.findByOrderByViewsDesc();
        return chefs.stream().map(chef -> DtoMapper.chefToBrief(chef)).collect(Collectors.toList());
    }

    public void addViews(Integer chefId, int views) {
        Optional<Chef> optionalChef = chefRepo.findById(chefId);
        if(optionalChef.isEmpty()) {
            throw new NotFoundException("chef");
        }
        Chef chef = optionalChef.get();
        chef.addViews(views);
        chefRepo.save(chef);
    }
}
