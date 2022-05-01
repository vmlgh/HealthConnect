package com.healthconnect.platform.repository.common;

import java.util.List;

import com.healthconnect.platform.entity.common.DegreeMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import com.healthconnect.platform.dto.common.SimpleDto;

@Repository
public interface MedicalDegreeRepository extends JpaRepository<DegreeMaster, Long> {

	@Query("SELECT new com.healthconnect.platform.dto.common.SimpleDto(d.recordId, d.name) FROM DegreeMaster d")
	List<SimpleDto> fetchMedicalDegrees();

	@Nullable
	DegreeMaster findByName(String name);
}

