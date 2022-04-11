package com.healthconnect.platform.webapp.controller.user;

import com.healthconnect.platform.dto.common.AccessDto;
import com.healthconnect.platform.webapp.common.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {

    public void validatePlatform(AccessDto loginDto) {
        if(loginDto.getPlatformId() == 0) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Platform id is required.");
        }
    }
}

