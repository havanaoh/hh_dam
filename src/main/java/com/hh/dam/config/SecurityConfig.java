package com.hh.dam.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.hh.dam.entity.MemberRole;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http
			.authorizeHttpRequests((auth) -> auth
					.requestMatchers("/security-login", "/security-login/login", "/security-login/join").permitAll()
					.requestMatchers("/security-login/admin").hasRole(MemberRole.ADMIN.name())
                    .requestMatchers("/security-login/info").hasAnyRole(MemberRole.ADMIN.name(), MemberRole.USER.name())
                    .anyRequest().authenticated()
            );
		
		http
        .logout((auth) -> auth
                .logoutUrl("/security-signin/signout")
        );

		http
        .formLogin((auth) -> auth.loginPage("/security-signin/signin")
                .loginProcessingUrl("/security-signin/loginProc")
                .failureUrl("/security-signin/signin")
                .defaultSuccessUrl("/security-signin")
                .usernameParameter("loginId")
                .passwordParameter("password")

                .permitAll()
        );


		http
        .csrf((auth) -> auth.disable());

		return http.build();
		
	}
	@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){


        return new BCryptPasswordEncoder();
	}

}
