package com.moveo.epicure.repo;

import com.moveo.epicure.entity.ChosenMeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChosenMealRepo extends JpaRepository<ChosenMeal, Integer> {

}
