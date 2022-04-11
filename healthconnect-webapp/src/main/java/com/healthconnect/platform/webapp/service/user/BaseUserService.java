package com.healthconnect.platform.webapp.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;

import com.healthconnect.platform.dto.common.BaseAccessDto;
import com.healthconnect.platform.dto.user.UserSignUpResponse;
import com.healthconnect.platform.entity.core.User;
import com.healthconnect.platform.enums.AppPageName;
import com.healthconnect.platform.enums.UserStatus;
import com.healthconnect.platform.enums.UserType;
import com.healthconnect.platform.repository.user.UserRepository;
import com.healthconnect.platform.security.JwtTokenUtil;
import com.healthconnect.platform.webapp.common.ApiException;

public abstract class BaseUserService {
	
	@Autowired
    protected UserRepository userRepository;
	
	@Autowired
    protected Environment environment; //what is this
	
	@Autowired
	protected JwtTokenUtil jwtTokenUtil;

	protected User getUserByEmailOrMobileNumber(BaseAccessDto loginDto , boolean isEmail) {
    	if(isEmail)
    		return findUserByEmailAndService(loginDto);
    	return findUserByMobileAndService(loginDto);
    }
	
	protected  User findUserByEmailAndService(BaseAccessDto loginDto) {
        return userRepository.findByEmailAndUserType(loginDto.getEmailOrPhone(), loginDto.getUserType());
    }
	
	protected User findUserByMobileAndService(BaseAccessDto loginDto) {
        return userRepository.findByMobileNumberAndUserType(loginDto.getEmailOrPhone(), loginDto.getUserType());
    }
	
	protected void validateIfSignUpAllowed(User user) {
        if(user == null) {
            return;
        }
        if(user.isDeleted() || user.getStatus() == UserStatus.BLOCKED) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "User is disabled. Please contact admin.");
        }else {
            throw new ApiException(HttpStatus.BAD_REQUEST, String.format("User already exist for service type '%s'. Try login instead.", user.getUserType().getValue().toUpperCase()));
        }
    }
	
	protected UserSignUpResponse generateSignUpResponse(User user, boolean isEmail) {
        UserSignUpResponse userSignupResponse = new UserSignUpResponse();
        userSignupResponse.setEmailVerified(false);
        userSignupResponse.setMobileVerified(false);
        userSignupResponse.setId(user.getRecordId());
        userSignupResponse.setUserType(user.getUserType());
        userSignupResponse.setEmail(user.getEmail());
        userSignupResponse.setPhone(user.getMobileNumber());
        userSignupResponse.setUsername(user.getUsername());
        userSignupResponse.setUserStatus(UserStatus.PROFILE_INCOMPLETE.name());
        userSignupResponse.setUserId(user.getUserId());
        userSignupResponse.setToken(jwtTokenUtil.generateToken(user));
        userSignupResponse.setLandingPage(identifyUserSingUpLandingPage(user));
        user.setToken(userSignupResponse.getToken());
        return userSignupResponse;
    }

    private String identifyUserSingUpLandingPage(User user) {
    	String signUplandingPage = null;
    	if(user.getUserType().equals(UserType.DOCTOR)) {
    		signUplandingPage = AppPageName.DOCTOR_PERSONAL_INFO.name();
    	}else if(user.getUserType().equals(UserType.PATIENT)) {
    		signUplandingPage = AppPageName.PATIENT_REG_INFO.name();
    	}
    	return signUplandingPage;
	}
}
