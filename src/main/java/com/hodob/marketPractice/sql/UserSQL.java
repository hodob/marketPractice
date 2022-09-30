package com.hodob.marketPractice.sql;

import com.hodob.marketPractice.dto.UserDTO;

public class UserSQL {

	public String UserSingUp(UserDTO userDTO) {        
        StringBuilder query = new StringBuilder();
        query.append("insert into test values('");
        if(userDTO.getId().equals(null)) {
        	query.append("notID,");
        } else {
        	query.append(userDTO.getId()+"','");
        }
        query.append(userDTO.getPw()+"')");
        return query.toString();
//       return "insert into test values('${id}','${pw}')"; 이 한줄로 실행해도 잘된다. 
    }

}
