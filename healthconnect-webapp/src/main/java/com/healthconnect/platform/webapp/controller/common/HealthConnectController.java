package com.healthconnect.platform.webapp.controller.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.healthconnect.platform.common.ApiResponse;
import com.healthconnect.platform.dto.common.CommonDto;
import com.healthconnect.platform.dto.common.SimpleDto;
import com.healthconnect.platform.dto.speciality.SpecialityDto;
import com.healthconnect.platform.entity.common.SpecialityMaster;
import com.healthconnect.platform.webapp.common.ApiException;
import com.healthconnect.platform.webapp.service.common.HealthConnectService;

import io.swagger.annotations.Api;


@Api(value="HealthConnect Apis", description="Operations pertaining to generic operations")
@RestController
@RequestMapping("/master")
public class HealthConnectController {

    @Autowired
    private HealthConnectService healthConnectService;

    @GetMapping("/speciality")
    public ApiResponse<List<CommonDto>> listSpecialities(){
        return new ApiResponse(HttpStatus.OK, "success", healthConnectService.findAllSpeciality());
    }
    
    @GetMapping("/speciality/{name}")
    public ApiResponse<SpecialityDto> getSpecialityByName(@PathVariable String name){
        return new ApiResponse(HttpStatus.OK, "success", healthConnectService.getSpecialityByName(name));
    }
    
    @GetMapping("/bloodGroups")
    public ApiResponse<List<SimpleDto>> listBloodGroups(){
        return new ApiResponse(HttpStatus.OK, "success", healthConnectService.getBloodGroups());
    }

	/*
	 * @GetMapping("/regd-councils") public ApiResponse<List<SimpleDto>>
	 * listRegdCouncils(){ return new ApiResponse(HttpStatus.OK, "success",
	 * healthConnectService.findAllMedicalCouncils()); }
	 * 
	 * @GetMapping("/medical-degree") public ApiResponse listMedDegrees(){ return
	 * new ApiResponse(HttpStatus.OK, "success",
	 * healthConnectService.findAllMedicalDegrees()); }
	 * 
	 * @GetMapping("/institute") public ApiResponse<List<SimpleDto>>
	 * listMedDegrees(@RequestParam String college){ if(college.length() < 3) {
	 * throw new ApiException(HttpStatus.BAD_REQUEST,
	 * "At least 3 characters required."); } return new ApiResponse(HttpStatus.OK,
	 * "success", healthConnectService.findMedicalColleges(college)); }
	 */

}
