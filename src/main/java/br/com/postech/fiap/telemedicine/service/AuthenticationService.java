package br.com.postech.fiap.telemedicine.service;

import br.com.postech.fiap.telemedicine.dto.LoginRequest;
import br.com.postech.fiap.telemedicine.dto.LoginResponse;
import br.com.postech.fiap.telemedicine.entities.User;
import br.com.postech.fiap.telemedicine.exceptions.HandledException;
import br.com.postech.fiap.telemedicine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public LoginResponse authenticate(LoginRequest login) {
        User user = null;
        user = userRepository.findByEmailAndType(login.getEmail(), login.getType());

        if (user == null) {
            throw new HandledException("User does not exist.");
        }

        if (!passwordEncoder.matches(login.getPassword(), user.getPassword())) {
            throw new HandledException("Incorrect password.");
        }

        return new LoginResponse(user.getId(), jwtUtil.generateToken(user));
    }
}