package com.healthconnect.platform.webapp.controller.patient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.healthconnect.platform.dto.common.BaseMasterDataDto;
import com.healthconnect.platform.dto.common.CommonDto;
import com.healthconnect.platform.dto.patient.*;
import com.healthconnect.platform.util.HealthConnectUtility;
import com.healthconnect.platform.webapp.common.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import com.healthconnect.platform.common.ApiResponse;
import com.healthconnect.platform.webapp.service.patient.PatientService;

import io.swagger.annotations.Api;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import static com.healthconnect.platform.enums.PatientMedicalRecordFileType.*;

@Api(value = "Patient APIs", description = "Operations pertaining to patient")
@RestController
@RequestMapping("/patient")
public class PatientController {

	@Autowired
	private PatientService patientService;
	
	@Secured({ "ROLE_PATIENT" })
	@PostMapping("/register")
	public ApiResponse<PatientRegisterResponseDto> register(@RequestBody @Valid PatientRegisterDto patientDto) {
		return new ApiResponse<>(HttpStatus.OK, "success", patientService.register(patientDto));
	}

	@Secured({ "ROLE_PATIENT"})
	@PutMapping("/{patientId}/profile-image")
	public ApiResponse<CommonDto> createMedicalRecord(@RequestParam String imageUrl, @PathVariable String patientId) {
		long userId = HealthConnectUtility.extractRecordIdFromUserId(patientId);
		return new ApiResponse<>(HttpStatus.OK, "success", patientService.updateProfileImage(imageUrl, userId));
	}
	
	@Secured({ "ROLE_PATIENT" })
	@GetMapping("/{patientId}")
	public ApiResponse<PatientProfileDto> getPatientProfile(@PathVariable String patientId) {
		long userId = HealthConnectUtility.extractRecordIdFromUserId(patientId);
		return new ApiResponse<>(HttpStatus.OK, "success", patientService.getPatientProfile(userId));
	}	

	@Secured({"ROLE_PATIENT"})
	@PutMapping("/{patientId}")
	public ApiResponse<PatientRegisterResponseDto> updatePatientProfile(@PathVariable String patientId,@RequestBody @Valid PatientRegisterDto patientRegisterDto) {
		long userId = HealthConnectUtility.extractRecordIdFromUserId(patientId);
		return new ApiResponse<>(HttpStatus.OK, "success", patientService.updatePatientProfile(userId,patientRegisterDto));
	}
}
