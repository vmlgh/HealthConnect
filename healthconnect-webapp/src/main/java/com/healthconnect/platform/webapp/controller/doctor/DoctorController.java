package com.healthconnect.platform.webapp.controller.doctor;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthconnect.platform.common.ApiResponse;
import com.healthconnect.platform.dto.common.SimpleDto;
import com.healthconnect.platform.dto.hospital.PhysicianHospitalDto;
import com.healthconnect.platform.dto.patient.PatientProfileDto;
import com.healthconnect.platform.dto.physician.EducationalQualificationDto;
import com.healthconnect.platform.dto.physician.PhysicianDto;
import com.healthconnect.platform.dto.physician.PhysicianProfileDto;
import com.healthconnect.platform.util.HealthConnectUtility;
import com.healthconnect.platform.webapp.service.doctor.PhysicianService;

import io.swagger.annotations.Api;

@Api(value="Doctor APIs", description="Operations pertaining to doctor")
@RestController
@RequestMapping("/doctor")
public class DoctorController {

	@Autowired
	private PhysicianService doctorService;

    @Secured({"ROLE_DOCTOR"})
    @PostMapping
    public ApiResponse<PhysicianDto> createDoctor(@RequestBody @Valid PhysicianDto physician) {
        return new ApiResponse<>(HttpStatus.OK, "success", doctorService.createDoctor(physician));
    }

    @PostMapping("/education")
    public ApiResponse<SimpleDto> addQualification(@RequestBody @Valid List<EducationalQualificationDto> educationalQualificationDtos){
        return new ApiResponse(HttpStatus.OK, "success", doctorService.addEducationalQualification(educationalQualificationDtos));
    }
    
    @PostMapping("/hospital")
    public ApiResponse<PhysicianProfileDto> addDoctorWorkHospital(@RequestBody @Valid List<PhysicianHospitalDto> physicianHospitalDtos){
        return new ApiResponse(HttpStatus.OK, "success", doctorService.addDoctorWorkHospital(physicianHospitalDtos));
    }
    
    @Secured({ "ROLE_DOCTOR" })
	@GetMapping("/{doctorId}")
	public ApiResponse<PhysicianProfileDto> getPhysicianProfile(@PathVariable String doctorId) {
		long userId = HealthConnectUtility.extractRecordIdFromUserId(doctorId);
		return new ApiResponse<>(HttpStatus.OK, "success", doctorService.generatePhysicianProfile(userId, false));
	}
    
	@GetMapping("/speciality/{specName}")
	public ApiResponse<List<PhysicianProfileDto>> getPhysicianBySpeciality(@PathVariable String specName) {
		return new ApiResponse<>(HttpStatus.OK, "success", doctorService.findBySpecialityName(specName));
	}
}