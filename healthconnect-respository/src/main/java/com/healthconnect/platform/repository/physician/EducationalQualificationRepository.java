package com.healthconnect.platform.repository.physician;

import com.healthconnect.platform.entity.physician.EducationalQualification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface EducationalQualificationRepository extends JpaRepository<EducationalQualification, Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM EducationalQualification WHERE recordId IN (:deleteIds)")
    void deleteAllById(@Param("deleteIds") List<Long> deleteIds);
}

