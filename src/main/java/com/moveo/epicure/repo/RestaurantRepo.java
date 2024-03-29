package com.moveo.epicure.repo;

import com.moveo.epicure.entity.Restaurant;
import com.moveo.epicure.util.QueryUtil;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepo extends JpaRepository<Restaurant, Integer> {
    List<Restaurant> findTop3ByOrderByPopularityDesc();

    @Query(QueryUtil.getRestaurantsByParams)
    List<Restaurant> findByParams(@Param("minPrice") int minPrice, @Param("maxPrice") int maxPrice
            , @Param("longitude") double longitude, @Param("latitude") double latitude, @Param("distance") int distance
            , @Param("open") boolean open, @Param("rating") int rating);

    @Query(QueryUtil.getRestaurantsByParams)
    List<Restaurant> findByParamsPage(@Param("minPrice") int minPrice, @Param("maxPrice") int maxPrice
            , @Param("longitude") double longitude, @Param("latitude") double latitude, @Param("distance") int distance
            , @Param("open") boolean open, @Param("rating") int rating, Pageable pageable);

    @Query(QueryUtil.restaurantByIdWithMeals)
    Optional<Restaurant> findRestaurantWithMeals(@Param("id") Integer id);

    Optional<Integer> findIdByName(String name);

    @Query(QueryUtil.searchByName)
    List<Restaurant> searchRestaurants(@Param("name") String name);
}
