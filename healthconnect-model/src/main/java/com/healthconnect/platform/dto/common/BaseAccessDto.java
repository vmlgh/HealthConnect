package com.healthconnect.platform.dto.common;

import javax.validation.constraints.NotEmpty;

import com.healthconnect.platform.enums.UserType;

public class BaseAccessDto {

	@NotEmpty(message = "{emailOrPhone.empty.error}")
	private String emailOrPhone;

	private long platformId;

	private UserType userType;

	public String getEmailOrPhone() {
		return emailOrPhone;
	}

	public void setEmailOrPhone(String emailOrPhone) {
		this.emailOrPhone = emailOrPhone;
	}

	public long getPlatformId() {
		return platformId;
	}

	public void setPlatformId(long platformId) {
		this.platformId = platformId;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

}
