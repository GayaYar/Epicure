package com.moveo.epicure.repo;

import com.moveo.epicure.entity.Meal;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MealRepo extends JpaRepository<Meal, Integer> {
    @Query("SELECT m FROM Meal m JOIN FETCH m.choices WHERE m.id=:id")
    Optional<Meal> findMealWithChoices(@Param("id") Integer id);
}
