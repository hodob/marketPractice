package com.hodob.marketPractice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hodob.marketPractice.dto.UserDTO;
import com.hodob.marketPractice.mapper.UserMapper;

@Service
public class UserService {
	 @Autowired UserMapper mapper;
	   public void signUp(UserDTO userDTO) {
	      mapper.UserSingUp(userDTO);
	   }
}
