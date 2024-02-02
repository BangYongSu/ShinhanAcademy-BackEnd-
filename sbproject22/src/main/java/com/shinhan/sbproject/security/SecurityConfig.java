package com.shinhan.sbproject.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.RequiredArgsConstructor;

@Configuration  //실행 할때 자동으로 실행됨
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
  public BCryptPasswordEncoder passwordEncoder(){
      return new BCryptPasswordEncoder();
  }
  //무조건접근가능
  private static final String[] WHITE_LIST = {
          "/auth/**", "/sample1","/v3/**", "/swagger-ui/**","/rest/**","/emp/**"
  };

  private static final String[] USER_LIST = {
  		"/webboard/**","/sample2"
  };
  private static final String[] ADMIN_LIST = {
          "/sample3"
  };

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
      http
      .csrf(c -> c.disable()) //토큰임 토큰없으면 로그인 불가
          //.cors(c -> c.disable())
              .authorizeHttpRequests(auth -> {
                  auth.requestMatchers(WHITE_LIST).permitAll()
                      .requestMatchers(USER_LIST).hasRole("USER")
                      .requestMatchers(ADMIN_LIST).hasRole("ADMIN")
                      .anyRequest().authenticated();
                       
                  });
      http.formLogin(login -> login
              .loginPage("/auth/login")//form 기반인증, 기본적으로 HTTPSession 이용 
//              .loginProcessingUrl("/auth/login")// default 로그인
//              .usernameParameter("mid") //이걸 쓰면 login.html에 아이디 password의 이름을 바꿀 수 있음
              .defaultSuccessUrl("/auth/loginSuccess")
              .permitAll())
              .logout(out -> out
                      .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"))
                      .logoutSuccessUrl("/auth/login")
                      .invalidateHttpSession(true))
              .exceptionHandling(handling -> handling.accessDeniedPage("/auth/accessDenined"));         
       return http.build();
  }

  
}