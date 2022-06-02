package com.moveo.epicure.repo;

import com.moveo.epicure.entity.Cart;
import com.moveo.epicure.util.QueryUtil;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepo extends JpaRepository<Cart, Integer> {
    Optional<Cart> findByCustomerIdAndCurrentTrue(Integer customerId);

    List<Cart> findByCustomerIdAndCurrentFalse(Integer customerId);

    @Query(QueryUtil.getCurrentCartWithMeals)
    Optional<Cart> findCurrentWithMeals(@Param("id") Integer customerId);

}
