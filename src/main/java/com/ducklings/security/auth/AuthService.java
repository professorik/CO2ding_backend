package com.ducklings.security.auth;

import com.ducklings.domain.user.UserService;
import com.ducklings.domain.user.model.User;
import com.ducklings.security.auth.model.*;
import com.ducklings.security.jwt.JwtProvider;

import org.springframework.http.HttpStatus;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {
	private final UserService userService;
	private final JwtProvider jwtProvider;
	private final PasswordEncoder passwordEncoder;

	public AuthService(UserService userService, JwtProvider jwtProvider, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.jwtProvider = jwtProvider;
		this.passwordEncoder = passwordEncoder;
	}

	public AuthResponse performLogin(AuthorizationRequest authorizationRequest) {
		var userDetails = userService.loadUserByUsername(authorizationRequest.getUsername());
		if (passwordsDontMatch(authorizationRequest.getPassword(), userDetails.getPassword())) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid password");
		}
		return AuthResponse.of(jwtProvider.generateToken(userDetails), jwtProvider.generateRefreshToken(userDetails));
	}

	private boolean passwordsDontMatch(String rawPw, String encodedPw) {
		return !passwordEncoder.matches(rawPw, encodedPw);
	}

	public AuthResponse performRegistration(RegistrationRequest registrationRequest) {
		if (userService.isUserExist(registrationRequest.getLogin(), registrationRequest.getEmail())){
			throw new ResponseStatusException(HttpStatus.CONFLICT, "The username/email is already exist");
		}
		var userDetails = userService.saveUser(registrationRequest);
		return AuthResponse.of(jwtProvider.generateToken(userDetails), jwtProvider.generateRefreshToken(userDetails));
	}

	public AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
		String requestRefreshToken = refreshTokenRequest.getRefreshToken();
		var userDetails = userService.loadUserByUsername(jwtProvider.getLoginFromToken(requestRefreshToken));
		return AuthResponse.of(jwtProvider.generateToken(userDetails), jwtProvider.generateRefreshToken(userDetails));
	}

	public AuthResponse replacePassword(ForgottenPasswordReplacementRequest forgottenPasswordReplacementRequest) {
		String token = forgottenPasswordReplacementRequest.getToken();
		String password = forgottenPasswordReplacementRequest.getNewPassword();
		var userDetails = userService.loadUserByUsername(jwtProvider.getLoginFromToken(token));
		userDetails = userService.updatePasswordById(userDetails.getId(), password);
		return AuthResponse.of(jwtProvider.generateToken(userDetails), jwtProvider.generateRefreshToken(userDetails));
	}

	public void replacePassword(String email) {
		var userDetails = userService.loadUserByEmail(email);
		System.out.println("Token: \u001B[35m" + jwtProvider.generateToken(userDetails) + "\u001B[0m");
	}

	public AuthResponse changePassword(String newPassword) {
		var userDetails = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		userService.updatePasswordById(userDetails.getId(), newPassword);
		return AuthResponse.of(jwtProvider.generateToken(userDetails), jwtProvider.generateRefreshToken(userDetails));
	}
}
