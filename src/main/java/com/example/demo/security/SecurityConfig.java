package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import static com.example.demo.Constants.USERS_API_URL;

@Configuration
public class SecurityConfig {

	@Bean
	public UserDetailsManager userDetailsManager(DataSource dataSource) {
		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

		jdbcUserDetailsManager.setUsersByUsernameQuery(
				"SELECT user_id, pw, active FROM members WHERE user_id = ?"
		);

		jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
				"SELECT user_id, role FROM roles WHERE user_id = ?"
		);

		return jdbcUserDetailsManager;
	}
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
		httpSecurity.authorizeHttpRequests(config -> config
				.requestMatchers(HttpMethod.GET, USERS_API_URL)
				.hasRole("EMPLOYEE")
				.requestMatchers(HttpMethod.GET, String.format("%s/%s", USERS_API_URL, "**"))
				.hasRole("EMPLOYEE")
				.requestMatchers(HttpMethod.POST, USERS_API_URL)
				.hasRole("MANAGER")
				.requestMatchers(HttpMethod.PUT, USERS_API_URL)
				.hasRole("MANAGER")
				.requestMatchers(HttpMethod.DELETE, String.format("%s/%s", USERS_API_URL, "**"))
				.hasRole("ADMIN")
		);

		// use basic authentication
		httpSecurity.httpBasic();

		// disable CSRF, in general not required for stateless REST APIs use POST, PUT, DELETE, and/or PATCH
		httpSecurity.csrf().disable();

		return httpSecurity.build();
	}

}
