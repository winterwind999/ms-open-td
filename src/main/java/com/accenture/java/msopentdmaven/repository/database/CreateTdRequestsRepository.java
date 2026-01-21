package com.accenture.java.msopentdmaven.repository.database;

import com.accenture.java.msopentdmaven.repository.database.entity.CreateTdRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreateTdRequestsRepository extends JpaRepository<CreateTdRequest, String> {
    @Query(value = "SELECT * FROM create_td_requests td " +
            "WHERE td.attempt_count < 3 AND td.status <> 'SUCCESSFUL'",
            nativeQuery = true
    )
    List<CreateTdRequest> findFailedRequests();

}
