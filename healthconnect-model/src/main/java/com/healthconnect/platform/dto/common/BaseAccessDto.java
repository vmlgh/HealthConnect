package com.healthconnect.platform.dto.common;

import javax.validation.constraints.NotEmpty;

import com.healthconnect.platform.enums.UserType;

public class BaseAccessDto {

	@NotEmpty(message = "{emailOrPhone.empty.error}")
	private String email;

	private UserType userType;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

}
