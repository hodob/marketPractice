package com.hodob.marketPractice.mapper;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;

import com.hodob.marketPractice.dto.UserDTO;
import com.hodob.marketPractice.sql.UserSQL;

@Mapper
public interface UserMapper {
	
	 @InsertProvider(type = UserSQL.class, method = "UserSingUp")
	 public void UserSingUp(UserDTO userDTO);
}
