package br.com.postech.fiap.telemedicine;

import br.com.postech.fiap.telemedicine.dto.LoginRequest;
import br.com.postech.fiap.telemedicine.dto.LoginResponse;
import br.com.postech.fiap.telemedicine.entities.User;
import br.com.postech.fiap.telemedicine.enums.UserType;
import br.com.postech.fiap.telemedicine.exceptions.HandledException;
import br.com.postech.fiap.telemedicine.repository.UserRepository;
import br.com.postech.fiap.telemedicine.service.AuthenticationService;
import br.com.postech.fiap.telemedicine.service.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class AutheticationServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void authenticate_success() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("thomaz.palmeira@example.com");
        loginRequest.setPassword("12345");
        loginRequest.setType(UserType.PATIENT);

        User u = new User();
        u.setId(1L);

        when(userRepository.findByEmailAndType(loginRequest.getEmail(), loginRequest.getType())).thenReturn(u);
        when(!passwordEncoder.matches(loginRequest.getPassword(), u.getPassword())).thenReturn(true);
        when(jwtUtil.generateToken(u)).thenReturn("TOKEN");

        LoginResponse response = authenticationService.authenticate(loginRequest);

        assertEquals(response.getToken(), "TOKEN");
        assertEquals(response.getUserId(), 1L);
    }

    @Test
    void authenticate_user_not_found() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("thomaz.palmeira@example.com");
        loginRequest.setPassword("12345");
        loginRequest.setType(UserType.PATIENT);

        when(userRepository.findByEmailAndType(loginRequest.getEmail(), loginRequest.getType())).thenReturn(null);

        assertThrows(HandledException.class, () -> authenticationService.authenticate(loginRequest));
    }

    @Test
    void invalid_password() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("thomaz.palmeira@example.com");
        loginRequest.setPassword("12345");
        loginRequest.setType(UserType.PATIENT);

        User u = new User();

        when(userRepository.findByEmailAndType(loginRequest.getEmail(), loginRequest.getType())).thenReturn(u);
        when(!passwordEncoder.matches(loginRequest.getPassword(), u.getPassword())).thenReturn(false);

        assertThrows(HandledException.class, () -> authenticationService.authenticate(loginRequest));
    }

}
