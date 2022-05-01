package com.healthconnect.platform.webapp.controller.user;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.healthconnect.platform.dto.common.AccessDto;
import com.healthconnect.platform.dto.user.AppLoginResponse;
import com.healthconnect.platform.dto.user.UserSignUpResponse;
import com.healthconnect.platform.webapp.service.user.UserService;
import com.healthconnect.platform.common.ApiResponse;

import io.swagger.annotations.Api;

@Api(value="User APIs", description="Operations pertaining to user")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ApiResponse<UserSignUpResponse> signUp(@RequestBody @Valid AccessDto loginDto){
        UserSignUpResponse response = userService.signUp(loginDto);
        return new ApiResponse(HttpStatus.OK, "Email verification link sent to your email.", response);
    }
    
	
    @PostMapping("/login") 
    public ApiResponse<AppLoginResponse> login(@RequestBody @Valid AccessDto loginDto) { 
    	AppLoginResponse response = userService.login(loginDto); 
    	return new ApiResponse(HttpStatus.OK, "success", response); 
    }
    
    @GetMapping("/verify-email")
    public String verifyEmail(@RequestParam("id") String id) {
        if(id == null) {
            return "Invalid URL. Please contact admin.";
        }
        id = id.replace(" ", "+");
        return userService.verifyUserEmail(id);
    }


}
