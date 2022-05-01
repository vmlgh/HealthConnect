package com.healthconnect.platform.webapp.job;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import com.healthconnect.platform.common.CryptoUtility;
import com.healthconnect.platform.dto.common.OutboxJob;
import com.healthconnect.platform.entity.appointment.Appointment;
import com.healthconnect.platform.entity.common.HealthConnectJob;
import com.healthconnect.platform.entity.core.User;
import com.healthconnect.platform.entity.physician.Physician;
import com.healthconnect.platform.enums.EmailContentKey;
import com.healthconnect.platform.enums.JobType;
import com.healthconnect.platform.repository.common.HealthConnectJobRepository;
import com.healthconnect.platform.repository.HealthConnectRepository;
import com.healthconnect.platform.webapp.service.common.BaseService;
import com.healthconnect.platform.webapp.service.common.SmtpService;
//import com.healthconnect.platform.webapp.service.common.SmsService;


public abstract class BaseHealthConnectJobService extends BaseService<HealthConnectJob> {

    private static final Logger logger = LoggerFactory.getLogger(BaseHealthConnectJobService.class);

    @Autowired
    protected HealthConnectJobRepository jobRepository;

    @Autowired
    protected SpringTemplateEngine templateEngine;

    @Autowired
    protected SmtpService smtpService;
    
    @Autowired
    protected HealthConnectRepository healthConnectRepository;
    
    public static final String WELCOME_PHRASE = "Welcome to ";

	/*
	 * @Autowired protected SmsService smsService;
	 */

    protected HealthConnectJob toHealthConnectOutboxJob(OutboxJob outboxJob) {
        SmtpService.EmailContentMap<EmailContentKey, String> outboxContent = getOutboxContent(outboxJob);
        String content = outboxContent.get(EmailContentKey.CONTENT);
        outboxContent.remove(EmailContentKey.CONTENT);
        String outboxExtraData = outboxContent.toString();
        boolean doSendSms = outboxJob.getJobType().isSendSms();
        boolean doSendEmail = outboxJob.getJobType().isSendEmail();
        HealthConnectJob medicoJob = new HealthConnectJob();
        medicoJob.setSendSms(doSendSms);
        medicoJob.setSendEmail(doSendEmail);
        medicoJob.setJobType(JobType.Notification);
        medicoJob.setJobName(outboxJob.getJobType().name());
        medicoJob.setContent(content);
        medicoJob.setExtraData(outboxExtraData);
        medicoJob.setAttempt(0);
        medicoJob.setEmailPending(doSendEmail);
        medicoJob.setSmsPending(doSendSms);
        if(outboxJob.getJobData()[0] instanceof User) {
            User user = (User) outboxJob.getJobData()[0];
            setDefaultOnCreate(medicoJob, user);
        }else {
            medicoJob.setCreatedOn(LocalDateTime.now());
        }
        return medicoJob;
    }

    private SmtpService.EmailContentMap<EmailContentKey, String> getOutboxContent(OutboxJob job) {


        SmtpService.EmailContentMap<EmailContentKey, String> contentMap = new SmtpService.EmailContentMap<>(EmailContentKey.class);
        try {
            if (job.getJobType().isSendEmail()) {
                prepareEmailContent(job, contentMap);
            }
            return contentMap;
        } catch (Exception ex) {
            logger.error("email sent failed!", ex);
        }
        return null;
    }
    
    private void prepareEmailContent(OutboxJob job, SmtpService.EmailContentMap<EmailContentKey, String> contentMap){
        Context thCntx = new Context();
        User loggedInUser = null;
        Physician physician = null;
        Appointment appointment = null;
        String emailContent;
        switch (job.getJobType()) {
            case WELCOME:
                loggedInUser = (User) job.getJobData()[0];
                thCntx.setVariable("user", loggedInUser);
                thCntx.setVariable("emailVerificationLink", job.getJobData()[1]);
                emailContent = templateEngine.process(job.getJobType().getEmailTemplateId(), thCntx);
                contentMap.put(EmailContentKey.FROM, environment.getProperty("healthconnect.welcome.from.email"));
                contentMap.put(EmailContentKey.TO, loggedInUser.getEmail());
                contentMap.put(EmailContentKey.CC, environment.getProperty("healthconnect.welcome.admin.email"));
                contentMap.put(EmailContentKey.SUBJECT, getWelcomeSubjectLine("HealthConnect"));
                contentMap.put(EmailContentKey.CONTENT, CryptoUtility.toBase64_unsecure(emailContent));
                break;
            case APPOINTMENT:
            	loggedInUser = (User) job.getJobData()[0];
            	physician = (Physician)job.getJobData()[1];
            	appointment = (Appointment)job.getJobData()[2];
                thCntx.setVariable("user", loggedInUser);
                thCntx.setVariable("physician", physician);
                thCntx.setVariable("appointment", appointment);
                emailContent = templateEngine.process(job.getJobType().getEmailTemplateId(), thCntx);
                contentMap.put(EmailContentKey.FROM, environment.getProperty("healthconnect.welcome.from.email"));
                contentMap.put(EmailContentKey.TO, loggedInUser.getEmail());
                contentMap.put(EmailContentKey.CC, environment.getProperty("healthconnect.welcome.admin.email"));
                contentMap.put(EmailContentKey.SUBJECT, "Appointment confirmation - HealthConnect");
                contentMap.put(EmailContentKey.CONTENT, CryptoUtility.toBase64_unsecure(emailContent));
            	
        }
    }

    private String getWelcomeSubjectLine(String appendLine) {
		return WELCOME_PHRASE + appendLine;
	}

	protected void clearOutboxJob(HealthConnectJob healthConnectJob) {
        logger.info("Started clearing Notification job " + healthConnectJob.getRecordId());
        if (!healthConnectJob.hasMaxAttemptsReached()) {

        	healthConnectJob.setAttempt(healthConnectJob.getAttempt() + 1);

            logger.info("Clearing job# " + healthConnectJob.getRecordId());

            if (healthConnectJob.isSendEmail() && healthConnectJob.isEmailPending()) {
                boolean emailSent;
                try {
                    SmtpService.EmailContentMap<EmailContentKey, String> emailContent = new SmtpService.EmailContentMap<>(EmailContentKey.class);
                    emailContent.fillAllByString(healthConnectJob.getExtraData());
                    emailContent.put(EmailContentKey.CONTENT, healthConnectJob.getContent());
                    emailSent = smtpService.sendEmail(emailContent);
                } catch (Exception ex) {
                    logger.debug("Mail sending resulted in exception! ignoring this job! error:  ", ex);
                    emailSent = false;
                }
                healthConnectJob.setEmailPending(!emailSent);
            }
        } else {
            if (healthConnectJob.isSendEmail()) {
            	healthConnectJob.setEmailPending(false);
            }
        }
        jobRepository.save(healthConnectJob);
        // if all the jobs has been completed, clear the job
        if (!healthConnectJob.isEmailPending()) {
            logger.info("Job#: " + healthConnectJob.getRecordId() + " of type: " + healthConnectJob.getJobType() + " cleared");
            if(healthConnectRepository.getEntityManager().contains(healthConnectJob)) {
                healthConnectRepository.getEntityManager().remove(healthConnectJob);
            }
            // make sure this session contains this job or it has been already deleted by other sessions and delete the job from db
        } else {
            logger.info("Job#: " + healthConnectJob.getRecordId() + " clear failed, will retry in next schedule");
            //logger.info("isSmsPending: " + healthConnectJob.isSmsPending() + " isEmailPending: " + healthConnectJob.isEmailPending());
        }
    }

    protected void clearPendingJobs(List<HealthConnectJob> recoveredJobs) {
        recoveredJobs.forEach(job -> {
            if(job.getJobType() == JobType.Notification) {
                clearOutboxJob(job);
            }
        });
    }

}

