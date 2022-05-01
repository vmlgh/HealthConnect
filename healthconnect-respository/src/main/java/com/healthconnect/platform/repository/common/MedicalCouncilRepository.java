package com.healthconnect.platform.repository.common;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import com.healthconnect.platform.dto.common.SimpleDto;
import com.healthconnect.platform.entity.common.MedicalCouncilMaster;

/**
 * @author aswain
 *
 */
@Repository
public interface MedicalCouncilRepository extends JpaRepository<MedicalCouncilMaster, Long> {

	/**
	 * @return
	 */
	 @Query("SELECT new com.healthconnect.platform.dto.common.SimpleDto(c.recordId, c.name) FROM MedicalCouncilMaster c")
	List<SimpleDto> fetchMedicalCouncils();

	 @Nullable
	 MedicalCouncilMaster findByName(String name);

}
