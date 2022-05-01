package com.healthconnect.platform.dto.patient;

import com.healthconnect.platform.dto.common.PersonalProfile;

public class PatientProfileDto {

	private PersonalProfile personalProfile;
	
	private PatientProfile professionalProfile;

	public PersonalProfile getPersonalProfile() {
		return personalProfile;
	}

	public void setPersonalProfile(PersonalProfile personalProfile) {
		this.personalProfile = personalProfile;
	}

	public PatientProfile getProfessionalProfile() {
		return professionalProfile;
	}

	public void setProfessionalProfile(PatientProfile professionalProfile) {
		this.professionalProfile = professionalProfile;
	}

	
}

