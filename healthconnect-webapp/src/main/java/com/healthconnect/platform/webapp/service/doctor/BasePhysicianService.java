package com.healthconnect.platform.webapp.service.doctor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;

import com.healthconnect.paltform.converter.PhysicianConverter;
import com.healthconnect.platform.dto.common.AddressDto;
import com.healthconnect.platform.dto.common.PersonalProfile;
import com.healthconnect.platform.dto.common.PricingDto;
import com.healthconnect.platform.dto.hospital.PhysicianHospitalDto;
import com.healthconnect.platform.dto.physician.EducationalQualificationDto;
import com.healthconnect.platform.dto.physician.PhysicianDto;
import com.healthconnect.platform.dto.physician.PhysicianProfessionalProfile;
import com.healthconnect.platform.dto.physician.PhysicianProfileDto;
import com.healthconnect.platform.entity.common.Address;
import com.healthconnect.platform.entity.common.DegreeMaster;
import com.healthconnect.platform.entity.common.MedicalCollegeMaster;
import com.healthconnect.platform.entity.common.MedicalCouncilMaster;
import com.healthconnect.platform.entity.common.SpecialityMaster;
import com.healthconnect.platform.entity.core.BaseEntity;
import com.healthconnect.platform.entity.core.User;
import com.healthconnect.platform.entity.physician.EducationalQualification;
import com.healthconnect.platform.entity.physician.Physician;
import com.healthconnect.platform.entity.physician.PhysicianPricing;
import com.healthconnect.platform.entity.physician.PhysicianProfile;
import com.healthconnect.platform.repository.physician.EducationalQualificationRepository;
import com.healthconnect.platform.repository.physician.PhysicianPricingRepository;
import com.healthconnect.platform.repository.physician.PhysicianRepository;
import com.healthconnect.platform.webapp.common.ApiException;
import com.healthconnect.platform.webapp.service.common.BaseService;
import com.healthconnect.platform.entity.hospital.Hospital;

public abstract class BasePhysicianService extends BaseService<BaseEntity> {

	@Autowired
    protected PhysicianRepository physicianRepository;
	
	@Autowired
	protected PhysicianPricingRepository pricingRespository;
	
	public static final long OTHER_SPECIALITY_ID = -100;
	
	public static final long OTHER_MEDICAL_COUNCIL_ID = -103;
	
	public static final long OTHER_MEDICAL_DEGREE_ID = -102;
	
	@Autowired
    protected EducationalQualificationRepository educationalQualificationRepository;
	
	protected Physician convertToPhysician(PhysicianDto physicianDto, User user) {
        Physician physician = physicianRepository.findPhysicianByUserId(user.getUserId());
        physician = PhysicianConverter.convertToPhysician(physicianDto, user, physician);
        user.setFirstName(physicianDto.getFirstName());
        user.setGenderType(physicianDto.getGender());
        user.setLastName(physicianDto.getLastName());
        user.setMobileNumber(physicianDto.getMobile());
        user.setTitle(resolveTitleByUserType(user));
        userRepository.save(user);
        physician.setUser(user);
        setDefaultOnCreate(physician, user);
        physician.setRegistrationNumber(physicianDto.getRegdNo());
        SpecialityMaster specialityMaster = getSpeciality(physicianDto, user);
        physician.setSpeciality(specialityMaster);
        physicianDto.setSpecialityId(specialityMaster.getRecordId());
        PhysicianProfile profileDetail = physician.getPhysicianProfile();
        if(profileDetail == null) {
            profileDetail = new PhysicianProfile();
            profileDetail.setCreatedOn(LocalDateTime.now());
            profileDetail.setCreatedBy(user);
        }else {
            profileDetail.setLastModifiedOn(LocalDateTime.now());
            profileDetail.setLastModifiedBy(user);
        }
        profileDetail.setYrsOfExp(physicianDto.getTotalExp());
        profileDetail.setProfileTitle(physicianDto.getProfileTitle());
        MedicalCouncilMaster medicalCouncilMaster = getRegdCouncil(physicianDto, user);
        profileDetail.setRegCouncil(medicalCouncilMaster);
        physicianDto.setRegdCouncilId(medicalCouncilMaster.getRecordId());
        profileDetail.setProfileDesc(physicianDto.getProfileDescription());
        profileDetail.setRegYear(physicianDto.getRegdYear());
        physician.setPhysicianProfile(profileDetail);
        physician.setFellowship(physicianDto.getFellowships());
        return physician;
    }
	
	protected SpecialityMaster getSpeciality(PhysicianDto physicianDto, User user) {
        SpecialityMaster specialityMaster;
        if(physicianDto.getSpecialityId() < 0 && physicianDto.getSpecialityId() == OTHER_SPECIALITY_ID ){
            specialityMaster = specialityRepository.findByName(physicianDto.getSpecialityName());
            if(specialityMaster == null){
                specialityMaster = new SpecialityMaster();
                specialityMaster.setName(physicianDto.getSpecialityName());
                specialityMaster.setDeleted(true);
                specialityMaster.setCreatedOn(LocalDateTime.now());
                specialityMaster.setCreatedBy(user);
                specialityMaster = specialityRepository.save(specialityMaster);
            }
        }else {
            specialityMaster = specialityRepository.findById(physicianDto.getSpecialityId()).get();
        }
        return specialityMaster;
    }
	
	protected MedicalCouncilMaster getRegdCouncil(PhysicianDto physicianDto, User user) {
        MedicalCouncilMaster medicalCouncilMaster;
        if(physicianDto.getRegdCouncilId() < 0 && physicianDto.getRegdCouncilId() == OTHER_MEDICAL_COUNCIL_ID ){
            medicalCouncilMaster = medCouncilRepository.findByName(physicianDto.getRegdCouncilName());
            if(medicalCouncilMaster == null){
                medicalCouncilMaster = new MedicalCouncilMaster();
                medicalCouncilMaster.setName(physicianDto.getRegdCouncilName());
                medicalCouncilMaster.setDeleted(true);
                medicalCouncilMaster.setCreatedOn(LocalDateTime.now());
                medicalCouncilMaster.setCreatedBy(user);
                medicalCouncilMaster = medCouncilRepository.save(medicalCouncilMaster);
            }
        }else {
            medicalCouncilMaster = medCouncilRepository.findById(physicianDto.getRegdCouncilId()).get();
        }
        return medicalCouncilMaster;
    }
	
	protected PhysicianProfessionalProfile populateProfessionalProfile(Physician physician, List<EducationalQualification> educationalQualifications, PhysicianProfileDto physicianProfileDto) {
        if(ObjectUtils.isEmpty(educationalQualifications)){
            throw new ApiException(HttpStatus.BAD_REQUEST, "Please add your educational qualification first.");
        }
       
        PhysicianProfessionalProfile professionalProfile = new PhysicianProfessionalProfile();
        professionalProfile.setEducationalQualifications(educationalQualifications.stream().map(EducationalQualification :: toEducationalQualificationDto).collect(Collectors.toList()));
        professionalProfile.setFellowships(physician.getFellowship());
        professionalProfile.setProfileDesc(physician.getPhysicianProfile().getProfileDesc());
        professionalProfile.setRating(physician.getPhysicianProfile().getAverageRatings());
        professionalProfile.setRegdCouncil(physician.getPhysicianProfile().getRegCouncil().getName());
        professionalProfile.setRegdYear(physician.getPhysicianProfile().getRegYear());
       // professionalProfile.setRegistrationNumber(physician.getPhysicianProfile().getRegistrationNumber());
        professionalProfile.setSpeciality(physician.getSpeciality().getName());
        professionalProfile.setYearsOfExp(physician.getPhysicianProfile().getYrsOfExp());
        //professionalProfile.setNotifications(getActiveNotifications(physician.getUser().getRecordId()).stream().map(Notification::toNotificationDto).collect(Collectors.toList()));
        PhysicianPricing pricing = pricingRespository.findPhysicianPricingByPhysicianRecordId(physician.getRecordId());
        PricingDto pricingDto = new PricingDto();
        pricingDto.setPrice(pricing.getPrice());
        pricingDto.setUnit(pricing.getUnit());
        professionalProfile.setPricing(pricingDto);
        physicianProfileDto.setPhysicianProfessionalProfile(professionalProfile);
        return professionalProfile;
    }
	
	protected PersonalProfile populatePersonalProfile(Physician physician, PhysicianProfile dbProfile, PhysicianProfileDto physicianProfileDto) {
        PersonalProfile personalProfile = new PersonalProfile();
        User user = physician.getUser();
        personalProfile.setId(user.getRecordId());
        personalProfile.setEmail(user.getEmail());
        personalProfile.setEmailVerified(user.isEmailVerified());
        personalProfile.setMobileVerified(user.isMobileVerified());
        personalProfile.setMobile(user.getMobileNumber());
        personalProfile.setFirstName(user.getFirstName());
        personalProfile.setLastName(user.getLastName());
        personalProfile.setUserId(user.getUserId());
        personalProfile.setUserStatus(user.getStatus().name());
        physicianProfileDto.setPersonalProfile(personalProfile);
        return personalProfile;
    }
	
	protected void populateWorkProfile(Physician physician, Set<Hospital> hospitals, PhysicianProfileDto physicianProfileDto) {
        List<PhysicianHospitalDto> hospitalDtos = new ArrayList<>();
        for(Hospital hospital : hospitals) {
            physicianProfileDto.setWorkProfileExist(true);
            PhysicianHospitalDto dto = new PhysicianHospitalDto();
            dto.setHospitalId(hospital.getRecordId());
            dto.setName(hospital.getName());
            Address address = hospital.getAddress();
            dto.setAddress(new AddressDto(address.getLocality(), address.getCity(), address.getPinCode()));
            hospitalDtos.add(dto);
        }
        physicianProfileDto.getPhysicianProfessionalProfile().setHospitals(hospitalDtos);
    }
	
	/*
	 * protected List<EducationalQualificationDto>
	 * filterAndDeleteEdQualifications(List<EducationalQualificationDto>
	 * educationalQualificationDtos) { List<EducationalQualificationDto>
	 * qualificationDtos =
	 * educationalQualificationDtos.stream().filter(qualification ->
	 * qualification.isDelete() && qualification.getId() >
	 * 0).collect(Collectors.toList()); if(!qualificationDtos.isEmpty()) {
	 * educationalQualificationDtos.removeAll(qualificationDtos);
	 * deleteEducationalQualification(qualificationDtos); } return
	 * educationalQualificationDtos; }
	 */
	
	protected void deleteEducationalQualification(List<EducationalQualificationDto> qualificationDtos) {
        List<Long> deleteIds = qualificationDtos.stream().map(EducationalQualificationDto::getId).collect(Collectors.toList());
        educationalQualificationRepository.deleteAllById(deleteIds);
    }
	
	protected EducationalQualification convertToEducationalQualification(EducationalQualificationDto qualificationDto, User user, Physician physician) {
        MedicalCollegeMaster medicalCollegeMaster = getCollege(qualificationDto, user);
        DegreeMaster degreeMaster = getDegree(qualificationDto, user);
        EducationalQualification educationalQualification = new EducationalQualification();
        educationalQualification.setCollegeMaster(medicalCollegeMaster);
        educationalQualification.setDegree(degreeMaster);
        educationalQualification.setHighestQual(qualificationDto.isHighest());
        educationalQualification.setCollegeName(medicalCollegeMaster.getName());
        educationalQualification.setDegreeName(degreeMaster.getName());
        educationalQualification.setRecordId(qualificationDto.getId());
        educationalQualification.setPhysician(physician);
        educationalQualification.setCompletionYear(qualificationDto.getYearOfCompletion());
        educationalQualification.setRecordId(qualificationDto.getId());
        setDefaultOnCreate(educationalQualification, user);
        return educationalQualification;
    }
	
	protected DegreeMaster getDegree(EducationalQualificationDto qualificationDto, User user) {
        DegreeMaster degreeMaster;
        if(qualificationDto.getDegreeId() < 0 && qualificationDto.getDegreeId() == OTHER_MEDICAL_DEGREE_ID ){
            degreeMaster = degreeRepository.findByName(qualificationDto.getDegreeName());
            if(degreeMaster == null){
                degreeMaster = new DegreeMaster();
                degreeMaster.setName(qualificationDto.getDegreeName());
                degreeMaster.setDeleted(true);
                degreeMaster.setCreatedOn(LocalDateTime.now());
                degreeMaster.setCreatedBy(user);
                degreeMaster = degreeRepository.save(degreeMaster);
            }
        }else {
            degreeMaster = degreeRepository.findById(qualificationDto.getDegreeId()).get();
        }
        return degreeMaster;
    }
	
	protected MedicalCollegeMaster getCollege(EducationalQualificationDto qualificationDto, User user) {
        MedicalCollegeMaster medicalCollegeMaster;
        if(qualificationDto.getCollegeId() == 0 || qualificationDto.getCollegeId() < 0){
            medicalCollegeMaster = collegeRepository.findByName(qualificationDto.getCollegeName());
            if(medicalCollegeMaster == null){
                medicalCollegeMaster = new MedicalCollegeMaster();
                medicalCollegeMaster.setName(qualificationDto.getCollegeName());
                medicalCollegeMaster.setDeleted(true);
                medicalCollegeMaster.setCreatedOn(LocalDateTime.now());
                medicalCollegeMaster.setCreatedBy(user);
                medicalCollegeMaster = collegeRepository.save(medicalCollegeMaster);
            }
        }else {
            medicalCollegeMaster = collegeRepository.findById(qualificationDto.getCollegeId()).get();
        }
        return medicalCollegeMaster;
    }
	
	protected Hospital resolveDuplicacyAndSaveHospital(PhysicianHospitalDto hospitalDtoRequest, AddressDto addressDto, User user) {
    	Hospital hospital  = null;
    	if(!StringUtils.isEmpty(addressDto.getPinCode()))
    		hospital = hospitalRepository.findByNameAndPinCode(hospitalDtoRequest.getName(), addressDto.getPinCode(),false);
        if(hospital == null) {
            hospital = new Hospital();
            hospital.setName(hospitalDtoRequest.getName());
            hospital.setAddressName(addressDto.getFullAddress());
            setDefaultOnCreate(hospital, user);
            Address address = resolveDuplicacyAndGetAddress(addressDto,user);
            hospital.setAddress(address);
            hospitalRepository.save(hospital);
        }
        return hospital;
    }
}
