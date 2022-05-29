package com.moveo.epicure.repo;

import com.moveo.epicure.entity.Cart;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepo extends JpaRepository<Cart, Integer> {
    Optional<Cart> findByCustomerIdAndCurrentTrue(Integer customerId);

    @Query("SELECT c from Cart c join FETCH c.chosenMeals where c.id=:id")
    Optional<Cart> findCurrentWithMeals(@Param("id") Integer customerId);

}
