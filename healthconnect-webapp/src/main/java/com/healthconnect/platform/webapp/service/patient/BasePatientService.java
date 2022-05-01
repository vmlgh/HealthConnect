package com.healthconnect.platform.webapp.service.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.healthconnect.platform.entity.core.User;
import com.healthconnect.platform.entity.patient.Patient;
import com.healthconnect.platform.repository.patient.PatientRepository;
import com.healthconnect.platform.webapp.common.ApiException;
import com.healthconnect.platform.webapp.service.common.BaseService;

public abstract class BasePatientService extends BaseService<Patient> {
	
	@Autowired
	protected PatientRepository patientRespository;
	
	protected Patient validatePatientId(long patientId) {
        User user = (User) authService.getAuthentication().getDetails();
        Patient patient = patientRespository.findByUserRecordId(user.getRecordId());
        if(patient == null || patient.getUser().getRecordId() != patientId) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Patient not found.");
        }
        return patient;
    }

}
