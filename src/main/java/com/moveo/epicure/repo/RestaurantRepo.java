package com.moveo.epicure.repo;

import com.moveo.epicure.entity.Restaurant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepo extends JpaRepository<Restaurant, Integer> {
    List<Restaurant> findTop3ByOrderByPopularityDesc();

    @Query("SELECT r FROM Restaurant r WHERE r.price >= :minPrice and r.price <= :maxPrice  and "
            + "SQRT((r.longitude-:longitude)*(r.longitude-:longitude) + (r.latitude-:latitude)*(r.latitude-:latitude))"
            + "<=:distance and (r.open = true or r.open = :open) and r.rating = :rating")
    List<Restaurant> findByParams(@Param("minPrice") int minPrice, @Param("maxPrice") int maxPrice
            , @Param("longitude") double longitude, @Param("latitude") double latitude, @Param("distance") int distance
            , @Param("open") boolean open, @Param("rating") int rating);
}
