package com.moveo.epicure.repo;

import com.moveo.epicure.entity.Permit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermitRepo extends JpaRepository<Permit, String> {

}
