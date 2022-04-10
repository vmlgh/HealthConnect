package com.healthconnect.platform.webapp.service.user;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.healthconnect.platform.common.CryptoUtility;
import com.healthconnect.platform.common.StringUtil;
import com.healthconnect.platform.dto.common.AccessDto;
import com.healthconnect.platform.dto.user.UserSignUpResponse;
import com.healthconnect.platform.entity.core.Role;
import com.healthconnect.platform.entity.core.User;
import com.healthconnect.platform.enums.OtpType;
import com.healthconnect.platform.enums.RoleType;
import com.healthconnect.platform.enums.UserStatus;
import com.healthconnect.platform.enums.UserType;
import com.healthconnect.platform.repository.role.RoleRepository;
import com.healthconnect.platform.util.HealthConnectUtility;
import com.healthconnect.platform.webapp.service.common.OtpService;

@Transactional
@Service
public class UserServiceImpl extends BaseUserService implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	/*
	 * @Autowired private PlatformRepository platformRepository;
	 */

    @Autowired
    private OtpService otpService;
    
    @Autowired
    protected RoleRepository roleRepository;

    @Override
    public UserSignUpResponse signUp(AccessDto accessDto) {
    	boolean isEmail = StringUtil.checkValidEmail(accessDto.getEmailOrPhone());
    	User user = getUserByEmailOrMobileNumber(accessDto ,isEmail);
        validateIfSignUpAllowed(user);
        if(!isEmail) {
            otpService.verifyOtp(accessDto.getEmailOrPhone(), accessDto.getOtp(), OtpType.SignUp);
        }
        user = new User();
        if(isEmail) 
            user.setEmail(accessDto.getEmailOrPhone());
        else 
            user.setMobileNumber(accessDto.getEmailOrPhone());
        user.setUsername(accessDto.getEmailOrPhone());
        user.setPassword(BCrypt.hashpw(accessDto.getPassword(), BCrypt.gensalt()));
        user.setUserType(accessDto.getUserType());
        user.setEmailVerified(false);
        user.setProfileImageUrl(environment.getProperty("default.profile.image.url"));
        user.setMobileVerified(!isEmail);
        user.setStatus(UserStatus.PROFILE_INCOMPLETE);
        user.setRoles(getRoleByService(accessDto.getUserType()));
        user.setCreatedOn(LocalDateTime.now());
        user.setAttempt(0);
        user = userRepository.save(user);
        user.setUserId(HealthConnectUtility.generateUserId(user.getRecordId(), getCharByService(user.getUserType())));
        /*if(isEmail) {
            jobService.addOutboxJob(new OutboxJob(OutboxJobType.WELCOME, user, getEmailVerificationLink(user.getUserId())));
        }*/
        UserSignUpResponse response = generateSignUpResponse(user, isEmail);
        response.setMobileVerified(!isEmail);
        //response.setProfileMasterData(generateProfileLaunchResponse(user.getUserType()));
        return response;
    }

    @Override
    public String verifyUserEmail(String encryptedId) {
        logger.info("Decrypting user id " + encryptedId);
        String userId = CryptoUtility.aesDecrypt(encryptedId);
        if(userId == null) {
            return "Malformed URL. Please try again";
        }
        User user = userRepository.findByUserId(userId);
        if(user == null) {
            return "System does not recognise you.";
        }
        if(user.isEmailVerified()) {
            return "Email already verified. Please proceed with login.";
        }else {
            user.setEmailVerified(true);
            userRepository.save(user);
            return "Email verified successfully. <a href='" + environment.getProperty("medicocyte.base.url") + "user/dashboard?id=" + encryptedId + "'>Click here</a> to go to your profile.";
            //return "Email verified successfully.";
        }
    }
    
    protected String getCharByService(UserType serviceType){
        String result = "ME";
        switch (serviceType) {
            case DOCTOR:
                result = serviceType.getValue().toUpperCase().substring(0,2);
                break;
            case PATIENT:
                result = serviceType.getValue().toUpperCase().substring(0,2);
                break;
        }
        return result;
    }
    
    protected Set<Role> getRoleByService(UserType userType){
        Set<Role> roles = new HashSet<>();
        switch (userType) {
            case DOCTOR:
                Role role = roleRepository.findByNameAndDeleted(RoleType.DOCTOR, false);
                roles.add(role);
                break;
            case PATIENT:
                Role rolePatient = roleRepository.findByNameAndDeleted(RoleType.PATIENT, false);
                roles.add(rolePatient);
                break;
        }
        Role role = roleRepository.findByNameAndDeleted(RoleType.USER, false);
        roles.add(role);
        return roles;
    }
}