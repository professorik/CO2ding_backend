package com.ducklings.security.auth.model;

import lombok.Data;

@Data
public class AuthorizationRequest {
	private String username;
	private String password;
}
