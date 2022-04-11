package com.healthconnect.platform.webapp.job;

//import com.healthconnect.platform.dto.common.OutboxJob;
import com.healthconnect.platform.entity.core.User;

public interface HealthConnectJobService {

    //void addOutboxJob(OutboxJob outboxJob);

    void clearMedicoPendingJobs();
}
