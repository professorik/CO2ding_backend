package com.ducklings.security.auth.model;

import lombok.Data;

@Data
public class AuthResponse {
	private String accessToken;
	private String refreshToken;

	public static AuthResponse of(String access, String refresh) {
		AuthResponse response = new AuthResponse();
		response.setAccessToken(access);
		response.setRefreshToken(refresh);
		return response;
	}
}
