package com.healthconnect.platform.webapp.service.common;

import com.healthconnect.platform.dto.common.CommonDto;
import com.healthconnect.platform.entity.common.HealthConnectOtp;
import com.healthconnect.platform.entity.core.User;
import com.healthconnect.platform.enums.OtpType;

public interface OtpService {

    void saveOtp(HealthConnectOtp otp);

    void verifyOtp(CommonDto commonDto);
    void verifyOtp(String encryptedOtpAndEmailOrPhone);
    void sendOtp(String emailOrPhone);
    void verifyOtp(String mobilePhone, String otp, OtpType otpType);
    void verifyOtp(String mobilePhone, String otp);
    void sendOtp(String emailOrPhone, OtpType otpType);

	void sendOtp(String emailOrPhone, OtpType otpType, User user);
}
