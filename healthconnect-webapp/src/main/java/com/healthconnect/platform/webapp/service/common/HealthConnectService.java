package com.healthconnect.platform.webapp.service.common;

import java.util.List;

import com.healthconnect.platform.dto.common.CommonDto;
import com.healthconnect.platform.dto.common.SimpleDto;
import com.healthconnect.platform.dto.speciality.SpecialityDto;
import com.healthconnect.platform.entity.common.SpecialityMaster;

public interface HealthConnectService {

    List<SimpleDto> findAllSpeciality();
    List<CommonDto> getBloodGroups();
    SpecialityDto getSpecialityByName(String name);
    
	/*
	 * List<SimpleDto> findAllMedicalCouncils();
	 * 
	 * List<SimpleDto> findAllMedicalDegrees();
	 * 
	 * List<SimpleDto> findMedicalColleges(String college);
	 * 
	 * List<CommonDto> getAllRegistrationTypes();
	 */    
    
}

