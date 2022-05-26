package com.moveo.epicure.repo;

import com.moveo.epicure.entity.Cart;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepo extends JpaRepository<Cart, Integer> {

    Optional<Cart> findByCustomerIdAndCurrentTrue(Integer customerId);
}
