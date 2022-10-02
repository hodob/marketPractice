package com.hodob.marketPractice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class MarketPracticeApplication {
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		// Spring Security 5 권장하는 인코더
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}


	public static void main(String[] args) {
		SpringApplication.run(MarketPracticeApplication.class, args);
	}

}
