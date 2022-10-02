package com.hodob.marketPractice.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;

import com.hodob.marketPractice.filter.LoggingFilter;
import com.hodob.marketPractice.service.AccountService;




@Configuration
// @EnableWebSecurity Spring Boot 에서 자동 등록을 해주므로 생략 가능
public class SecurityConfig  {

    private final AccountService accountService;

    public SecurityConfig(AccountService accountService) {
        this.accountService = accountService;
    }

    // AccessDecisionManager
    public AccessDecisionManager accessDecisionManager() {
        /**
         * AccessDecisionManager -> AccessDecisionVoter -> webExpressionVoter -> setExpressionHandler -> DefaultWebSecurityExpressionHandler -> roleHierarchy
         * */
        // roleHierarchy
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER ");

        // DefaultWebSecurityExpressionHandler
        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setRoleHierarchy(roleHierarchy);

        // setExpressionHandler
        // WebExpressionVoter 를 사용하겠습니다.
        WebExpressionVoter webExpressionVoter = new WebExpressionVoter();
        webExpressionVoter.setExpressionHandler(handler);

        // AccessDecisionVoter
        // Voter 목록을 만듭니다.
        List<AccessDecisionVoter<? extends Object>> voters = Arrays.asList(webExpressionVoter);

        return new AffirmativeBased(voters);
    }

    public SecurityExpressionHandler securityExpressionHandler() {
        // roleHierarchy
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER ");

        // DefaultWebSecurityExpressionHandler
        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setRoleHierarchy(roleHierarchy);

        return handler;
    }

    @Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception {
        // get "/", get "/info" 으로 오는 요청은 인증을 하지않고 접근하는 설정
        http
                .authorizeRequests()
                .mvcMatchers(
                        "/",
                        "/info",
                        "/account/**",
                        "/signup/**"
                )
                .permitAll();
        // get "/admin" 으로 오는 요청은 ADMIN 권한을 가지고있는 사용자만 접근하는 설정
        http
                .authorizeRequests()
                .mvcMatchers("/admin")
                .hasRole("ADMIN")
                .mvcMatchers("user")
                .hasRole("USER");

        // 그외 모든 페이지는 단순 인증을 하면 접근하는 설정
        http
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .expressionHandler(securityExpressionHandler());
        //.accessDecisionManager(accessDecisionManager());

        // 특정 페이지 검증 필터 제외 하지만 WebSecurity 사용 권장
        //        http
        //                .authorizeRequests()
        //                .requestMatchers(PathRequest
        //                        .toStaticResources()
        //                        .atCommonLocations());

        /**
         * 기본적으로 사하는 SecurityContextHolder는 getContextHolderStrategy 설정 가능합니다.
         * SecurityContext 정보를 어떻게 유지할 것인가 어디까지 공유할 것인가 를 설정가능합니다.
         * 기본은 ThreadLocal 입니다.
         *
         * SecurityContextHolder.MODE_INHERITABLETHREADLOCAL 를 사용하면 현재
         * Thread 에서 하위 Thread 생성하는 Thread 에도 SecurityContextHolder가 공유가 됩니다.
         *
         * */
        //SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);

        //        http
        //                .logout()
        //                .logoutUrl("/logout")
        //                .logoutSuccessUrl("/");

        /* http
                .formLogin()
                .usernameParameter("my-username")
                .passwordParameter("my-password");*/

        // form 로그인 기능을 사용하겠다.
      /*  http
                .formLogin()
                .and()
                .httpBasic();*/

        http
                .formLogin()
                .loginPage("/signin")
                .permitAll();

        http
                .anonymous()
                .principal("anonymous");

        http
                .sessionManagement()
                .invalidSessionUrl("/login");

        http
                .sessionManagement()
                .sessionFixation()
                .changeSessionId()
                .invalidSessionUrl("/login");

        /**
         * expiredUrl 이전 로그인한 Session 값이 만료되 되면 이동되는 페이지 설정
         * maxSessionsPreventsLogin(true) 새로운 Session 의 로그인을 못하게 막을 수 있는 설정
         * */
        http
                .sessionManagement()
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true)
                .expiredUrl("/login");

        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.NEVER);

        http
                .exceptionHandling()
                .accessDeniedPage("/access-page");

        http
                .exceptionHandling()
                .accessDeniedHandler(new AccessDeniedHandler() {
                    @Override
                    public void handle(
                            HttpServletRequest request,
                            HttpServletResponse response,
                            AccessDeniedException accessDeniedException
                    ) throws IOException, ServletException {
                        UserDetails principal = (UserDetails) SecurityContextHolder
                                .getContext()
                                .getAuthentication()
                                .getPrincipal();
                        String username = principal.getUsername();

                        System.out.println(username);
                        response.sendRedirect("/access-denied");
                    }
                });

        http
                .rememberMe()
                .userDetailsService(accountService)
                //                .tokenValiditySeconds()
                //                .useSecureCookie(true)
                //                .alwaysRemember(true)
                .key("remember-me-sample");

        /**
         * 커스텀 필터 추가
         * WebAsyncManagerIntegrationFilter 기존에 맨 앞에 위치한 필터 앞에 커스텀 필터를 넣음으로서
         * 커스텀 필터가 최상위로 올라왔습니다.
         * */
        http.addFilterBefore(
                new LoggingFilter(),
                WebAsyncManagerIntegrationFilter.class
        );
        return http.build();
    }

    // 인메모리 유저 생성 방법
    //    @Override
    //    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    //        // password {암호화 방식} 을 추가하면 비밀번호를 해당 암호화로 변경됩니다.
    //        // {noop} Spring Security 기본 장착된 인코더
    //        auth
    //                .inMemoryAuthentication()
    //                .withUser("jjunpro-1")
    //                .password("{noop}123")
    //                .roles("USER");
    //
    //        auth
    //                .inMemoryAuthentication()
    //                .withUser("jjunpro-2")
    //                .password("{noop}123")
    //                .roles("ADMIN");
    //    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // 기본 제외 방법
    	 return (web) ->
        web
        .ignoring()
                .mvcMatchers("/favicon.ico")
                // Spring 프레임워크 제외방법
                .requestMatchers(PathRequest
                        .toStaticResources()
                        .atCommonLocations());
    }
}
