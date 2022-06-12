package com.moveo.epicure.repo;

import com.moveo.epicure.entity.LoginAttempt;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttemptRepo extends JpaRepository<LoginAttempt, Integer> {
    List<LoginAttempt> findTop9ByMailOrderByTimeDesc(String mail);
    Optional<LoginAttempt> findTop1ByMailOrderByTimeDesc(String mail);
}
