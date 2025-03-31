package emp.emp.auth.own.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import emp.emp.auth.own.dto.RegisterRequest;

public interface AuthService extends UserDetailsService {
	void register(RegisterRequest request);
}
