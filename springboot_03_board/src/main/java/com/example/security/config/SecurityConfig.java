package com.example.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.login.repository.UserRepository;
import com.example.security.jwt.JwtAuthenticationFilter;
import com.example.security.jwt.JwtAuthorizationFilter;
import com.example.security.service.CorsConfig;

//[1] POSTMAN에서 테스트
//POST http://localhost:8090/join
//boy, raw , json  => {"username":"min", "password":"1234"}
//

@Configuration
@EnableWebSecurity // spring security 활성화- spring security filter가 스프링 필터체인에 등록이 된다.
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig {

	@Autowired
	private UserRepository userReposiroty;

	@Autowired
	private CorsConfig corsConfig;

	@Bean
	public BCryptPasswordEncoder encodePassword() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		// 기존 거를 끊다.
		http.csrf().disable();

		// 세션끄기 : JWT를 사용하기 때문에 세션을 사용하지 않는다.
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		// 기본 로그인 폼을 제공하는데 사용 거부
		http.formLogin().disable();
		// 기본 제공 되는 걸 사용 거부
		http.httpBasic().disable();

		// 커스텀 필터 등록
		http.apply(new MyCustomerFilter());

		http.authorizeHttpRequests().antMatchers("/member/**").authenticated() // 인증만 되면 들어갈 수 있는 주소
				// .antMatchers("/manager/**").access("hasRole("ROLE_ADMIN") or
				// hasRole("ROLE_MANGER")")
				.antMatchers("/manager/**").hasAnyRole("ADMIN", "MANAGER") // ADMIN,MANAGER권한자만 접근 가능
				.antMatchers("/admin/**").hasRole("ADMIN") // ADMIN권한자만 접근 가능
				.anyRequest().permitAll();
		return http.build();
	}

	public class MyCustomerFilter extends AbstractHttpConfigurer<MyCustomerFilter, HttpSecurity> {
		@Override
		public void configure(HttpSecurity http) throws Exception {
			AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
			// @CrossOrigin(인증 X), Security Filter에 등록 인증(O)
			http.addFilter(corsConfig.corsFilter()).addFilter(new JwtAuthenticationFilter(authenticationManager))
					.addFilter(new JwtAuthorizationFilter(authenticationManager, userReposiroty));

		}
	}
}
