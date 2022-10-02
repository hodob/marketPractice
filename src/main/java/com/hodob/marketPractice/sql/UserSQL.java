package com.hodob.marketPractice.sql;

import com.hodob.marketPractice.dto.UserDTO;

public class UserSQL {

	public String UserSignUp(UserDTO userDTO) {        
        StringBuilder query = new StringBuilder();
        query.append("insert into user (user_id,user_pw) values('");
        if(userDTO.getUser_id().equals(null)) {
        	query.append("notID,");
        } else {
        	query.append(userDTO.getUser_id()+"','");
        }
        query.append(userDTO.getUser_pw()+"')");
        return query.toString();
//       return "insert into test values('${id}','${pw}')"; 이 한줄로 실행해도 잘된다. 
    }
	
	public String UserSignIn(UserDTO userDTO) {        
       return "select user_num from user where user_id = '${user_id}'"; 
    }

}
