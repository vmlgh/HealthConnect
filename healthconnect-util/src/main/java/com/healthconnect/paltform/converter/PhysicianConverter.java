package com.healthconnect.paltform.converter;

import com.healthconnect.platform.dto.physician.PhysicianDto;
import com.healthconnect.platform.entity.core.User;
import com.healthconnect.platform.entity.physician.Physician;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PhysicianConverter {

    public static Physician convertToPhysician(PhysicianDto physicianDto, User user, Physician physician) {
        if(physician == null) {
            physician = new Physician();
            physician.setCreatedOn(LocalDateTime.now());
            physician.setCreatedBy(user);
        }else {
            physician.setLastModifiedOn(LocalDateTime.now());
            physician.setLastModifiedBy(user);
        }
        physician.setDob(LocalDate.parse(physicianDto.getDob()));
        physician.setAge(LocalDate.now().getYear() - physician.getDob().getYear());
        return physician;
    }
}
