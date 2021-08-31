package com.ducklings.security.auth;

import com.ducklings.domain.user.model.User;
import com.ducklings.security.auth.model.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/auth")
public class AuthController {
	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/login")
	public AuthResponse login(@RequestBody AuthorizationRequest authorizationRequest) {
		return authService.performLogin(authorizationRequest);
	}

	@PostMapping("/register")
	public AuthResponse register(@RequestBody RegistrationRequest registrationRequest) {
		return authService.performRegistration(registrationRequest);
	}

	@PostMapping("/refresh")
	public AuthResponse refreshTokenPair(@RequestBody RefreshTokenRequest refreshTokenRequest) {
		return authService.refreshToken(refreshTokenRequest);
	}

	@PutMapping("/forgotten_password")
	public void forgotPasswordRequest(@RequestParam String email) {
		authService.replacePassword(email);
	}

	@PatchMapping("/forgotten_password")
	public AuthResponse forgottenPasswordReplacement(@RequestBody ForgottenPasswordReplacementRequest forgottenPasswordReplacementRequest) {
		return authService.replacePassword(forgottenPasswordReplacementRequest);
	}

	@PatchMapping("change_password")
	public AuthResponse changePassword(@RequestBody PasswordChangeRequest passwordChangeRequest) {
		return authService.changePassword(passwordChangeRequest.getNewPassword());
	}

	@GetMapping("me")
	public User whoAmI(@AuthenticationPrincipal User user) {
		return user;
	}
}
