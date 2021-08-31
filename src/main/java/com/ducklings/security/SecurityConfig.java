package com.ducklings.security;

import com.ducklings.domain.user.model.UserRole;
import com.ducklings.security.jwt.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private void applyRouteRestrictions(HttpSecurity http) throws Exception {
		http
				.authorizeRequests()
				.antMatchers("/v1/auth/**").permitAll()
				.antMatchers("/v1/co2/**").permitAll()
				.antMatchers("/v1/admin/**").hasRole(UserRole.ADMIN.toString())
				.anyRequest().permitAll();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.cors().and()
				.csrf().disable()
				.httpBasic().disable()
				.formLogin().disable()
				.exceptionHandling(rejectAsUnauthorized())
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		applyRouteRestrictions(http);
		http.addFilterBefore(tokenFilter(), UsernamePasswordAuthenticationFilter.class);
		http.addFilterBefore(filterChainExceptionHandler(), UsernamePasswordAuthenticationFilter.class);
	}

	private Customizer<ExceptionHandlingConfigurer<HttpSecurity>> rejectAsUnauthorized() {
		return exceptionHandling -> exceptionHandling.authenticationEntryPoint((request, response, authException) -> response.sendError(403));
	}

	@Bean
	public JwtFilter tokenFilter() {
		return new JwtFilter();
	}


	@Bean
	public FilterChainExceptionHandler filterChainExceptionHandler() {
		return new FilterChainExceptionHandler();
	}
}
