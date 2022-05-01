package com.healthconnect.platform.dto.patient;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.healthconnect.platform.dto.common.BaseMasterDataDto;
import com.healthconnect.platform.dto.common.CommonDto;
import com.healthconnect.platform.dto.common.ServiceSearchDto;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PatientMasterDataDto extends BaseMasterDataDto {


	private List<CommonDto> bloodGroups;
	
	private List<ServiceSearchDto>  healthConnectServices;
	
	private List<CommonDto> genders;
	
	public List<CommonDto> getBloodGroups() {
		return bloodGroups;
	}

	public void setBloodGroups(List<CommonDto> bloodGroups) {
		this.bloodGroups = bloodGroups;
	}

	public List<ServiceSearchDto> getHealthConnectServices() {
		return healthConnectServices;
	}

	public void setHealthConnectServices(List<ServiceSearchDto> healthConnectServices) {
		this.healthConnectServices = healthConnectServices;
	}

	public List<CommonDto> getGenders() {
		return genders;
	}

	public void setGenders(List<CommonDto> genders) {
		this.genders = genders;
	}
}

