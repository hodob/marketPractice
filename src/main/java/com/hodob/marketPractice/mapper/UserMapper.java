package com.hodob.marketPractice.mapper;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import com.hodob.marketPractice.dto.UserDTO;
import com.hodob.marketPractice.sql.UserSQL;

@Mapper
public interface UserMapper {
	
	 @InsertProvider(type = UserSQL.class, method = "UserSignUp")
	 public void UserSignUp(UserDTO userDTO);
	 
	 @SelectProvider(type = UserSQL.class, method = "UserSignIn")
	 public int UserSignIn(UserDTO userDTO);
}
