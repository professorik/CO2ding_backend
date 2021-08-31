package com.ducklings.security.auth.model;

import lombok.Data;

@Data
public class PasswordChangeRequest {
	private String newPassword;
}
