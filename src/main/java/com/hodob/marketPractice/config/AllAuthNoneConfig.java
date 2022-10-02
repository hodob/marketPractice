package com.hodob.marketPractice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * account 요청만 인증 없이 접근을 허용하는 Config
 */
//@Configuration
@Order(Ordered.LOWEST_PRECEDENCE - 10) // 실행 우선순위를 하위로 내립니다.
public class AllAuthNoneConfig {
	 
		@Bean
		public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception {
        http
                .antMatcher("/account/**")
                .authorizeRequests()
                .anyRequest()
                .permitAll();
        return http.build();
    }
}
