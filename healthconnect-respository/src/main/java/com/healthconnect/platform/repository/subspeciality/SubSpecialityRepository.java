package com.healthconnect.platform.repository.subspeciality;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.healthconnect.platform.dto.common.CommonDto;
import com.healthconnect.platform.entity.common.SubSpecialityMaster;

public interface SubSpecialityRepository extends JpaRepository<SubSpecialityMaster, Long> {
	
	@Query("SELECT new com.healthconnect.platform.dto.common.CommonDto(ssp.name, ssp.description) FROM SubSpecialityMaster ssp WHERE ssp.specialityMaster.name = :name")
	Set<CommonDto> findBySpecialityId(@Param("name") String name);
}
