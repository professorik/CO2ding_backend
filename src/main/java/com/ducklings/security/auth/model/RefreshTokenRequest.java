package com.ducklings.security.auth.model;

import lombok.Data;

@Data
public class RefreshTokenRequest {
	private String refreshToken;
}
