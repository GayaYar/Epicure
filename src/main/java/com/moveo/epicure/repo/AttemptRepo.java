package com.moveo.epicure.repo;

import com.moveo.epicure.entity.LoginAttempt;
import com.moveo.epicure.util.QueryUtil;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AttemptRepo extends JpaRepository<LoginAttempt, Integer> {
    List<LoginAttempt> findTop9ByMailOrderByTimeDesc(String mail);
    Optional<LoginAttempt> findTop1ByMailOrderByTimeDesc(String mail);

    @Query(QueryUtil.countByMailAndTimeRange)
    long countByMailInTime(@Param("mail") String mail, @Param("fromTime") Timestamp fromTime, @Param("toTime") Timestamp toTime);
}
