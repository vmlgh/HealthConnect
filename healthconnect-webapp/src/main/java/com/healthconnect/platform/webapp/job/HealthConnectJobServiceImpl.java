package com.healthconnect.platform.webapp.job;

import com.healthconnect.platform.dto.common.OutboxJob;
import com.healthconnect.platform.entity.common.HealthConnectJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalTime;
import java.util.List;

@Service
public class HealthConnectJobServiceImpl extends BaseHealthConnectJobService implements HealthConnectJobService {

    private static final Logger logger = LoggerFactory.getLogger(HealthConnectJobServiceImpl.class);

    @Override
    @Async
    public void addOutboxJob(OutboxJob outboxJob) {
        logger.info("Adding medico outbox job type " + outboxJob.getJobType().name());
        HealthConnectJob medicoJob = toHealthConnectOutboxJob(outboxJob);
        medicoJob = jobRepository.save(medicoJob);
        logger.debug(String.format("%s Job persisted with id# '%s' ", outboxJob.getJobType().name(), medicoJob.getRecordId()));
        if (outboxJob.getJobType().isImmediate()) {
            logger.debug("Job# " + medicoJob.getRecordId() + " is an immediate job, trying to clear it");
            clearOutboxJob(medicoJob);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    //@Scheduled(fixedDelay = 10000)
    public void clearMedicoPendingJobs() {
        logger.info("Scheduled clearing pending jobs: " + LocalTime.now());
            List<HealthConnectJob> recoveredJobs = jobRepository.findPendingJobs();
            logger.info("pending jobs: " + recoveredJobs.size());
            if (!recoveredJobs.isEmpty()) {
                clearPendingJobs(recoveredJobs);
            }
    }
}

