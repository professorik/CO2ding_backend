package com.ducklings.domain.user;

import com.ducklings.domain.user.model.User;
import com.ducklings.domain.user.model.UserRole;
import com.ducklings.security.auth.model.RegistrationRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepository {
	private final List<User> users = new ArrayList<>();
	private PasswordEncoder passwordEncoder;

	public UserRepository(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
		var regularUser = new User();
		regularUser.setUsername("regular");
		regularUser.setEmail("regular@mail.com");
		regularUser.setId(UUID.randomUUID());
		regularUser.setPassword(passwordEncoder.encode("password"));
		regularUser.setAuthorities(Set.of(UserRole.USER));
		this.users.add(regularUser);

		var adminUser = new User();
		adminUser.setUsername("privileged");
		adminUser.setEmail("privileged@mail.com");
		adminUser.setId(UUID.randomUUID());
		adminUser.setPassword(passwordEncoder.encode("password"));
		adminUser.setAuthorities(Set.of(UserRole.ADMIN));
		this.users.add(adminUser);
	}

	public Optional<User> findByUsername(String username) {
		return users.stream().filter(user -> user.getUsername().equals(username)).findAny();
	}

	public Optional<User> findByEmail(String email) {
		return users.stream().filter(user -> user.getEmail().equals(email)).findAny();
	}

	public List<User> findUsers() {
		return Collections.unmodifiableList(users);
	}

	public void createUserByEmail(String email) {
		var createdUser = new User();
		createdUser.setId(UUID.randomUUID());
		createdUser.setEmail(email);
		createdUser.setUsername(email);
		createdUser.setAuthorities(Set.of(UserRole.USER));
		users.add(createdUser);
	}

	public User createUser(RegistrationRequest registrationRequest) {
		var regularUser = new User();
		regularUser.setUsername(registrationRequest.getLogin());
		regularUser.setEmail(registrationRequest.getEmail());
		regularUser.setId(UUID.randomUUID());
		regularUser.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
		regularUser.setAuthorities(Set.of(UserRole.USER));
		users.add(regularUser);
		return regularUser;
	}

	public User updateById(UUID id, String password) {
		User userDet = users.stream().filter(user -> user.getId().equals(id)).findAny().get();
		userDet.setPassword(passwordEncoder.encode(password));
		return userDet;
	}
}
