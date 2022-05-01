package com.healthconnect.platform.repository.common;

import com.healthconnect.platform.entity.common.HealthConnectJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HealthConnectJobRepository extends JpaRepository<HealthConnectJob, Long> {

    @Query(" SELECT j from HealthConnectJob j WHERE j.deleted = false AND (j.emailPending = true OR j.smsPending = true)")
    List<HealthConnectJob> findPendingJobs();
}
