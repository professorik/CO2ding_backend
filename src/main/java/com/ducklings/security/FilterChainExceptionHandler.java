package com.ducklings.security;

import com.ducklings.security.jwt.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class FilterChainExceptionHandler extends OncePerRequestFilter {

	@Autowired
	@Qualifier("handlerExceptionResolver")
	private HandlerExceptionResolver resolver;

	@Override
	@SuppressWarnings("NullableProblems")
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
		try {
			filterChain.doFilter(request, response);
		} catch (JwtException e) {
			log.debug("Token exception in filter chain", e);
			resolver.resolveException(request, response, null, e);
		} catch (Exception e) {
			log.error("Unexpected exception", e);
			resolver.resolveException(request, response, null, e);
		}
	}
}
