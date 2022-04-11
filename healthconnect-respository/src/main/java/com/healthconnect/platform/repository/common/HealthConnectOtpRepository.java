package com.healthconnect.platform.repository.common;

import com.healthconnect.platform.entity.common.HealthConnectOtp;
import com.healthconnect.platform.enums.OtpType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthConnectOtpRepository extends JpaRepository<HealthConnectOtp, Long> {

	HealthConnectOtp findTopByEmailPhoneAndDeletedOrderByCreatedOnDesc(String identifier, boolean deleted);
    
	HealthConnectOtp findTopByEmailPhoneAndTypeAndDeletedOrderByCreatedOnDesc(String identifier, OtpType type, boolean deleted);
}
