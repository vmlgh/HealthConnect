package com.healthconnect.platform.repository.physician;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;

import com.healthconnect.platform.entity.physician.PhysicianPricing;

public interface PhysicianPricingRepository extends JpaRepository<PhysicianPricing, Long> {
	
	@Nullable
	@Query("SELECT p from PhysicianPricing p where p.physician.recordId = :recordId")
	PhysicianPricing findPhysicianPricingByPhysicianRecordId(@Param("recordId") long recordId);
	
}