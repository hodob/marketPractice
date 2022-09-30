package com.hodob.marketPractice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.hodob.marketPractice.dto.UserDTO;
import com.hodob.marketPractice.service.UserService;



@Controller
public class UserController {
	
	@Autowired private UserService userService;
	
	@GetMapping("/user/signInPage")
	public String toLoginPage() throws Exception {
		
		try {
			
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
		
		return "/user/signInPage";
	}
	
	@GetMapping("/user/signUpPage")
	public String toSignUpPage() throws Exception {
		
		try {
			
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
		
		return "/user/signUpPage";
	}
	@PostMapping("/user/signUpProcess")
	public String signUpProcess(UserDTO userDTO) throws Exception {
		
		try {
			System.out.println(userDTO);
			userService.signUp(userDTO);
			

		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
		
		return "/user/signUpPage";
	}

}
