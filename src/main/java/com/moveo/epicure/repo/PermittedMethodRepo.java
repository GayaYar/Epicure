package com.moveo.epicure.repo;

import com.moveo.epicure.entity.PermittedMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermittedMethodRepo extends JpaRepository<PermittedMethod, String> {

}
