package com.hh.dam.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(authorize -> authorize
						// 인증 없이 접근 가능(로그인, 회원가입)

						.requestMatchers("/", "/home","/signin", "/signup").permitAll()

						// ADMIN 권한이 있어야 접근 가능
						.requestMatchers("/admin").hasRole("ADMIN")

						// ADMIN 또는 USER 권한을 가진 사용자만 접근 가능
						.requestMatchers("/info", "/book/**", "/library/**", "/board/**").hasAnyRole("ADMIN", "USER")

						// 그 외 모든 요청은 인증된 사용자만 접근 가능
						.anyRequest().authenticated()
				)
				// 로그아웃 설정
				.logout((auth) -> auth
						// 로그아웃 요청 URL 설정
						.logoutUrl("/signout")

						// 로그아웃 성공 시 이동할 페이지 설정
						.logoutSuccessUrl("/signin")

						// 로그아웃 요청은 인증 없이도 가능
						.permitAll()
				)
				.formLogin((auth) -> auth
						.loginPage("/signin")  // 사용자 정의 로그인 페이지
						.loginProcessingUrl("/security-signin/loginProc")  // 로그인 처리 URL
						.usernameParameter("userId")  // 로그인 폼의 사용자명 필드
						.passwordParameter("password")  // 로그인 폼의 비밀번호 필드
						.defaultSuccessUrl("/home", true)  // 로그인 성공 후 이동할 경로
						.failureUrl("/signin?error=true")  // 로그인 실패 시 이동할 경로
						.permitAll()
				);
//				.sessionManagement(session -> session
//						.maximumSessions(1)  // 동시에 한 명의 사용자만 로그인 허용
//						.maxSessionsPreventsLogin(false)  // 기존 세션 종료를 막음, 새로운 로그인 차단
//				);

		return http.build();
	}

	// 사용자의 비밀번호 암호화하고 비교하는데 사용
	@Bean
	public PasswordEncoder passwordEncoder(){

		return new BCryptPasswordEncoder();
	}

}