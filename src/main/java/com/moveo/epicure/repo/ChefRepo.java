package com.moveo.epicure.repo;

import com.moveo.epicure.entity.Chef;
import com.moveo.epicure.util.QueryUtil;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChefRepo extends JpaRepository<Chef, Integer> {
    @Query(QueryUtil.getChefWithRestaurants)
    Optional<Chef> findByIdWithRestaurants(@Param("id") Integer id);

    List<Chef> findByOrderByViewsDesc();
    List<Chef> findByOrderByAddingDateDescViewsDesc();
}
