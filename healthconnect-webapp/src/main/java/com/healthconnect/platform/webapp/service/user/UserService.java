package com.healthconnect.platform.webapp.service.user;

import com.healthconnect.platform.dto.common.AccessDto;
import com.healthconnect.platform.dto.user.AppLoginResponse;
import com.healthconnect.platform.dto.user.UserSignUpResponse;

public interface UserService {

    UserSignUpResponse signUp(AccessDto loginDto);

    String verifyUserEmail(String encryptedId);
    
    AppLoginResponse login(AccessDto loginDto);

}

