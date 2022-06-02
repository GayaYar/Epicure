package com.moveo.epicure.repo;

import com.moveo.epicure.entity.Choice;
import com.moveo.epicure.entity.Meal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChoiceRepo extends JpaRepository<Choice, Integer> {
}
