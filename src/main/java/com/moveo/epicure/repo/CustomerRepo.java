package com.moveo.epicure.repo;

import com.moveo.epicure.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<User, Integer> {

    Optional<User> findByEmailAndPassword(String email, String password);
}
