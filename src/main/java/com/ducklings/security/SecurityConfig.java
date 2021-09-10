package com.ducklings.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.header.HeaderWriterFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private void applyRouteRestrictions(HttpSecurity http) throws Exception {
		http
				.authorizeRequests()
				.antMatchers("/v1/co2/distribution/*").permitAll()
				.anyRequest().denyAll();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.cors().and()
				.csrf().disable()
				.httpBasic().disable()
				.formLogin().disable()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		applyRouteRestrictions(http);
		http.addFilterBefore(filterHandler(), HeaderWriterFilter.class);
	}

	@Bean
	public FilterChainExceptionHandler filterHandler() {
		return new FilterChainExceptionHandler();
	}
}
