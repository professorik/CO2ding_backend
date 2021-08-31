package com.ducklings.domain.user;

import com.ducklings.domain.user.model.User;
import com.ducklings.security.auth.model.RegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
	private final UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Not found"));
	}

	public boolean isUserExist(String login, String email){
		return userRepository.findByUsername(login).isPresent() || userRepository.findByEmail(email).isPresent();
	}

	public List<User> getAll() {
		return userRepository.findUsers();
	}

	public User saveUser(RegistrationRequest registrationRequest) {
		return userRepository.createUser(registrationRequest);
	}

	public User updatePasswordById(UUID id, String password) {
		return userRepository.updateById(id, password);
	}

	public User loadUserByEmail(String email) {
		return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Not found"));
	}
}
