package com.healthconnect.platform.webapp.service.patient;

import javax.validation.Valid;

import com.healthconnect.platform.dto.common.BaseMasterDataDto;
import com.healthconnect.platform.dto.common.CommonDto;
import com.healthconnect.platform.dto.patient.PatientProfileDto;
import com.healthconnect.platform.dto.patient.PatientRegisterDto;
import com.healthconnect.platform.dto.patient.PatientRegisterResponseDto;

public interface PatientService {

	PatientProfileDto getPatientProfile(long userId);
	
	PatientRegisterResponseDto register(@Valid PatientRegisterDto patientDto);
	
	BaseMasterDataDto getPatientMasterData();
	
	CommonDto updateProfileImage(String imageUrl, long patientId);
	
	PatientRegisterResponseDto updatePatientProfile(long patientId,PatientRegisterDto patientRegisterDto);
}

