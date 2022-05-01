package com.healthconnect.platform.webapp.job;

import com.healthconnect.platform.dto.common.OutboxJob;

public interface HealthConnectJobService {

    void addOutboxJob(OutboxJob outboxJob);

    void clearMedicoPendingJobs();
}
