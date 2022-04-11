package com.healthconnect.platform.repository.role;

import com.healthconnect.platform.entity.core.Role;
import com.healthconnect.platform.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{

    /*@Query("SELECT r from Role r where r.deleted = :deleted AND (r.serviceType = :serviceType OR r.serviceType is null)")
    Set<Role> findAllDefaultRolesByServiceType(@Param("serviceType") ServiceType serviceType, @Param("deleted") boolean deleted);*/

    Role findByNameAndDeleted(RoleType roleType, boolean deleted);
}
