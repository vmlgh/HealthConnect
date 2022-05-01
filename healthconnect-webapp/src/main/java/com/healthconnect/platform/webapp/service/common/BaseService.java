package com.healthconnect.platform.webapp.service.common;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;

import com.healthconnect.platform.dto.common.AddressDto;
import com.healthconnect.platform.dto.common.CommonDto;
import com.healthconnect.platform.dto.common.ServiceSearchDto;
import com.healthconnect.platform.dto.common.SimpleDto;
import com.healthconnect.platform.dto.speciality.SpecialityDto;
import com.healthconnect.platform.entity.common.Address;
import com.healthconnect.platform.entity.common.CityMaster;
import com.healthconnect.platform.entity.common.SpecialityMaster;
import com.healthconnect.platform.entity.common.SubSpecialityMaster;
import com.healthconnect.platform.entity.core.BaseEntity;
import com.healthconnect.platform.entity.core.User;
import com.healthconnect.platform.enums.BloodGroupType;
import com.healthconnect.platform.enums.GenderType;
import com.healthconnect.platform.enums.ServiceCategoryType;
import com.healthconnect.platform.repository.common.AddressRepository;
import com.healthconnect.platform.repository.common.CityRepository;
import com.healthconnect.platform.repository.common.MedicalCollegeRepository;
import com.healthconnect.platform.repository.common.MedicalCouncilRepository;
import com.healthconnect.platform.repository.common.MedicalDegreeRepository;
import com.healthconnect.platform.repository.physician.HospitalRepository;
import com.healthconnect.platform.repository.physician.PhysicianRepository;
import com.healthconnect.platform.repository.speciality.SpecialityRepository;
import com.healthconnect.platform.repository.subspeciality.SubSpecialityRepository;
import com.healthconnect.platform.repository.user.UserRepository;
import com.healthconnect.platform.security.service.AuthenticationFacadeService;
import com.healthconnect.platform.webapp.common.ApiException;

/**
 * A BaseService Class and Common Implementation
 */
public abstract class BaseService<T extends BaseEntity> {
	
	@Autowired
    protected Environment environment;
	
	@Autowired
    protected AddressRepository addressRepository;

	@Autowired
    protected CityRepository cityRepository;
	
	@Autowired
    protected AuthenticationFacadeService authService;
	
	@Autowired
    protected UserRepository userRepository;
	
	@Autowired
    protected SpecialityRepository specialityRepository;
	
	@Autowired
    protected SubSpecialityRepository subSpecialityRepository;
	
	 @Autowired 
	 protected MedicalCouncilRepository medCouncilRepository;
	 
	 @Autowired
	 protected MedicalDegreeRepository degreeRepository;
	 
	 @Autowired
	 protected MedicalCollegeRepository collegeRepository;
	 
	 @Autowired
	 protected HospitalRepository hospitalRepository;
	 
	 @Autowired
	 protected PhysicianRepository physicianRepository;

    protected  <T extends BaseEntity> T setDefaultOnCreate(T entity, User user){
        entity.setCreatedBy(user);
        entity.setCreatedOn(LocalDateTime.now());
        entity.setDeleted(false);
        return entity;
    }

    protected  <T extends BaseEntity> T setDefaultOnUpdate(T entity, User user){
        entity.setLastModifiedBy(user);
        entity.setLastModifiedOn(LocalDateTime.now());
        entity.setDeleted(false);
        return entity;
    }

    protected Address resolveDuplicacyAndGetAddress(AddressDto addressDto, User loggedInUser) {
    	Address address = null;
    	if(!StringUtils.isEmpty(addressDto.getLocality()))
    		address = addressRepository.findAddressByLocalityAndCity(addressDto.getLocality(), addressDto.getCity(), false);
        if(address == null) {
            address = new Address();
            BeanUtils.copyProperties(addressDto, address);
            address.setCityMaster(resolveDuplicacyAndGetCity(addressDto));
            setDefaultOnCreate(address, loggedInUser);
            address = addressRepository.save(address);
        }
        return address;
    }
    
    protected CityMaster resolveDuplicacyAndGetCity(AddressDto addressDto) {
        CityMaster cityMaster = cityRepository.findCityByNameAndState(addressDto.getCity(), addressDto.getState(), false);
        if(cityMaster == null) {
            cityMaster = new CityMaster();
            BeanUtils.copyProperties(addressDto, cityMaster);
            cityMaster = cityRepository.save(cityMaster);
        }
        return cityMaster;
    }
    
    protected List<CommonDto> getBloodGroups(){
    	List<CommonDto> bloodGroups = new ArrayList<>();
    	for(BloodGroupType userType : BloodGroupType.values()) {
    		CommonDto dto = new CommonDto();
    		dto.setKey(userType.name());
    		dto.setValue(userType.getValue());
    		bloodGroups.add(dto);
    	}
    	return bloodGroups;
    }
    
    protected List<CommonDto> getGenders(){
    	List<CommonDto> genders = new ArrayList<>();
    	for(GenderType userType : GenderType.values()) {
    		CommonDto dto = new CommonDto();
    		dto.setKey(userType.name());
    		dto.setValue(userType.getValue());
    		genders.add(dto);
    	}
    	return genders;
    }
    
    protected List<ServiceSearchDto> fetchAllMedicoServices() {
		List<ServiceSearchDto> medicoServices = new ArrayList<>();
		for (ServiceCategoryType category : ServiceCategoryType.values()) {
			List<ServiceSearchDto> searchServices = null;
			switch (category) {
			case SPECIALITY:
				List<SpecialityMaster> specialities = specialityRepository.findAll();
				searchServices = specialities.stream()
						.map(subSpeciality -> new ServiceSearchDto(subSpeciality.getName(),
								subSpeciality.getDescription(), category))
						.collect(Collectors.toList());
				medicoServices.addAll(searchServices);
				break;
				
			case SUB_SPECIALITY:
				List<SubSpecialityMaster> subSpecialities = subSpecialityRepository.findAll();
				searchServices = subSpecialities.stream()
						.map(subSpeciality -> new ServiceSearchDto(subSpeciality.getName(),
								subSpeciality.getDescription(), category))
						.collect(Collectors.toList());
				medicoServices.addAll(searchServices);
				break;
			}
		}
		return medicoServices;
	}
    
    protected List<CommonDto> fetchSpeciality(){
	    return specialityRepository.fetchSpeciality();
    }
    
    protected SpecialityDto fetchSpecialityByName(String name){
    	SpecialityDto specialityDto = new SpecialityDto();
    	SpecialityMaster speciality = specialityRepository.findByName(name);
    	if(speciality != null) {
    		Set<CommonDto> subSpecialities = subSpecialityRepository.findBySpecialityId(speciality.getName());
    		specialityDto.setName(speciality.getName());
    		specialityDto.setDescription(speciality.getDescription());
    		specialityDto.setId(speciality.getRecordId());
    		specialityDto.setSubSpecialityMasters(subSpecialities);
    	} else {
    		throw new ApiException(HttpStatus.BAD_REQUEST.value(), "Invalid Speciality name");
    	}
    	
    	return specialityDto;
    }
    
    protected String resolveTitleByUserType(User user) {
        String title = null;
        switch (user.getUserType()) {
            case DOCTOR:
                title = "Dr.";
                break;
        }
        if(title == null) {
            switch (user.getGenderType()) {
                case MALE:
                    title = "Mr.";
                    break;
                case FEMALE:
                    title = "Miss.";
                    break;
            }
        }
        return title;
    }
    
}
