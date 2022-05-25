package com.moveo.epicure.repo;

import com.moveo.epicure.entity.Meal;
import com.moveo.epicure.entity.Restaurant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealRepo extends JpaRepository<Meal, Integer> {
    List<Meal> findByRestaurant(Restaurant restaurant);
}
