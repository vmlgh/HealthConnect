package com.healthconnect.platform.repository.speciality;

import com.healthconnect.platform.dto.common.CommonDto;
import com.healthconnect.platform.dto.common.SimpleDto;
import com.healthconnect.platform.entity.common.SpecialityMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface SpecialityRepository extends JpaRepository<SpecialityMaster, Long> {

    @Query("SELECT new com.healthconnect.platform.dto.common.CommonDto(s.name, s.description) FROM SpecialityMaster s")
    List<CommonDto> fetchSpeciality();

    @Query("SELECT sp from SpecialityMaster sp WHERE sp.name = :name")
    SpecialityMaster findByName(@Param("name") String name);
    
	/*
	 * @Query("SELECT new com.healthconnect.platform.dto.common.CommonDto(ss.name, ss.description) FROM SubSpecialityMaster ss, SpecialityMaster sm WHERE sm.name = :name"
	 * ) Set<CommonDto> findSubSpeciality(@Param("name") String name);
	 */
}

