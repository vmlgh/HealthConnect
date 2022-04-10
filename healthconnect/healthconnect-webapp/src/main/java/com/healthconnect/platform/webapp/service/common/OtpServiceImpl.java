package com.healthconnect.platform.webapp.service.common;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.healthconnect.platform.dto.common.CommonDto;
import com.healthconnect.platform.entity.common.HealthConnectOtp;
import com.healthconnect.platform.entity.core.User;
import com.healthconnect.platform.enums.OtpType;

@Transactional
@Service
public class OtpServiceImpl implements OtpService {

	@Override
	public void saveOtp(HealthConnectOtp otp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void verifyOtp(CommonDto commonDto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void verifyOtp(String encryptedOtpAndEmailOrPhone) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendOtp(String emailOrPhone) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void verifyOtp(String mobilePhone, String otp, OtpType otpType) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void verifyOtp(String mobilePhone, String otp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendOtp(String emailOrPhone, OtpType otpType) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendOtp(String emailOrPhone, OtpType otpType, User user) {
		// TODO Auto-generated method stub
		
	}

	/*
	 * @Autowired private HealthConnectOtpRepository healthConOtpRepository;
	 * 
	 * @Autowired private MedicoJobService jobService;
	 * 
	 * @Override public void saveOtp(HealthConnectOtp otp) {
	 * otp.setCreatedOn(LocalDateTime.now()); medicoOtpRepository.save(otp); }
	 * 
	 * @Override public void verifyOtp(CommonDto commonDto) {
	 * verifyOtp(commonDto.getValue()); }
	 * 
	 * @Override public void verifyOtp(String mobilePhone, String otp, @Nullable
	 * OtpType otpType) { MedicoOtp medicoOtp; if (otpType == null) { medicoOtp =
	 * medicoOtpRepository.findTopByEmailPhoneAndDeletedOrderByCreatedOnDesc(
	 * mobilePhone, false); } else { medicoOtp =
	 * medicoOtpRepository.findTopByEmailPhoneAndTypeAndDeletedOrderByCreatedOnDesc(
	 * mobilePhone, otpType, false); } if (medicoOtp == null) { throw new
	 * ApiException(HttpStatus.BAD_REQUEST, "Try generating an OTP first."); } if
	 * (medicoOtp.getAttempt() > 4) { throw new ApiException(HttpStatus.BAD_REQUEST,
	 * "Maximum attempt reached. Try generating a new OTP."); } if
	 * (medicoOtp.getExpiry() < System.currentTimeMillis()) { throw new
	 * ApiException(HttpStatus.BAD_REQUEST,
	 * "OTP Expired. Try generating a new OTP."); } if
	 * (!medicoOtp.getOtp().equals(otp)) { throw new
	 * ApiException(HttpStatus.BAD_REQUEST, "OTP mismatch"); }
	 * medicoOtp.setDeleted(true); medicoOtpRepository.save(medicoOtp); }
	 * 
	 * @Override public void verifyOtp(String mobilePhone, String otp) {
	 * verifyOtp(mobilePhone, otp, null); }
	 * 
	 * @Override public void verifyOtp(String encryptedOtpAndEmailOrPhone) { String
	 * decryptedText =
	 * AesUtil.decryptWithDefaultPassphrase(encryptedOtpAndEmailOrPhone); if
	 * (decryptedText == null) { throw new ApiException(HttpStatus.BAD_REQUEST,
	 * "Invalid encryption."); } String[] identifierAndOtp =
	 * decryptedText.split("::"); String identifier = identifierAndOtp[0]; String
	 * userGivenOtp = identifierAndOtp[1]; verifyOtp(identifier, userGivenOtp); }
	 * 
	 * @Override public void sendOtp(String emailOrPhone) { sendOtp(new
	 * MedicoOtp(emailOrPhone, getOtp())); }
	 * 
	 * @Override public void sendOtp(String emailOrPhone, OtpType otpType) {
	 * sendOtp(new MedicoOtp(emailOrPhone, getOtp(), otpType)); }
	 * 
	 * @Override public void sendOtp(String emailOrPhone, OtpType otpType, User
	 * user) { sendOtp(new MedicoOtp(emailOrPhone, getOtp(), otpType), user); }
	 * 
	 * private void sendOtp(MedicoOtp medicoOtp) { saveOtp(medicoOtp); if
	 * (StringUtil.checkValidEmail(medicoOtp.getEmailPhone())) {
	 * jobService.addOutboxJob( new OutboxJob(OutboxJobType.EMAIL_OTP,
	 * medicoOtp.getEmailPhone(), medicoOtp.getOtp())); } else {
	 * jobService.addOutboxJob(new OutboxJob(OutboxJobType.MOBILE_VERIFICATION_OTP,
	 * medicoOtp.getEmailPhone(), medicoOtp.getOtp())); } }
	 * 
	 * private void sendOtp(MedicoOtp medicoOtp, User user) { saveOtp(medicoOtp);
	 * //To Use the same for other type ot otp ,user switch case for OtpType if
	 * (StringUtil.checkValidEmail(medicoOtp.getEmailPhone())) {
	 * jobService.addOutboxJob(new
	 * OutboxJob(OutboxJobType.MEDICO_FORGOT_PASSWORD_EMAIL_OTP, user, getOtp())); }
	 * else { jobService.addOutboxJob(new
	 * OutboxJob(OutboxJobType.MEDICO_FORGOT_PASSWORD_MOBILE_OTP, user, getOtp()));
	 * } }
	 */

}
