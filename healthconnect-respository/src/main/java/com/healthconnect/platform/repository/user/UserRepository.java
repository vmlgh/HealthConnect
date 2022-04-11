package com.healthconnect.platform.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import com.healthconnect.platform.entity.core.User;
import com.healthconnect.platform.enums.UserType;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Nullable
    User findByEmailAndUserType(String email, UserType serviceType);
    
    @Nullable
    User findByMobileNumberAndUserType(String phone, UserType userType);
    
    @Nullable
    User findByUserId(String userId);

}

