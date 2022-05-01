package com.healthconnect.platform.webapp.service.doctor;

import java.util.List;

import com.healthconnect.platform.dto.hospital.PhysicianHospitalDto;
import com.healthconnect.platform.dto.physician.EducationalQualificationDto;
import com.healthconnect.platform.dto.physician.PhysicianDto;
import com.healthconnect.platform.dto.physician.PhysicianProfileDto;

public interface PhysicianService {

	PhysicianDto createDoctor(PhysicianDto physician);
	
	PhysicianProfileDto generatePhysicianProfile(long userRecordId, boolean excludeWorkProfile);
	
    List<EducationalQualificationDto> addEducationalQualification(List<EducationalQualificationDto> educationalQualificationDtos);
    
    PhysicianProfileDto addDoctorWorkHospital(List<PhysicianHospitalDto> physicianHospitalDtos);
    
    List<PhysicianProfileDto> findBySpecialityName(String specName);

}

