package com.hodob.marketPractice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
//@Data
@Getter
@Setter
public class UserDTO {
	private int user_num;
	private String user_id;
	private String user_pw;

}
