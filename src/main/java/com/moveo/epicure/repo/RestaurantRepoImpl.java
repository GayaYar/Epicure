package com.moveo.epicure.repo;

import com.moveo.epicure.entity.Restaurant;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class RestaurantRepoImpl {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Restaurant> findOrderByPopularityLimitedTo(int limit) {
        return entityManager.createQuery("SELECT r FROM Restaurant r ORDER BY r.popularity DESC", Restaurant.class)
                .setMaxResults(limit).getResultList();
    }
}
