package br.com.postech.fiap.telemedicine.controller;

import br.com.postech.fiap.telemedicine.dto.*;
import br.com.postech.fiap.telemedicine.entities.User;
import br.com.postech.fiap.telemedicine.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Operation(summary = "Request auth token", description = "Request token for API passing credentials")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest loginForm, BindingResult bindingResult) {
        ApiResponse response = new ApiResponse(userService.authenticate(loginForm), "User is logged in", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/patient/register")
    public ResponseEntity<ApiResponse> register(@Valid  @RequestBody CreatePatientUserRequest patient) {
        ApiResponse response = new ApiResponse(userService.registerPatient(patient), "Login created successfully", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/doctor/register")
    public ResponseEntity<Object> register(@Valid @RequestBody CreateDoctorUserRequest doctor) {
        ApiResponse response = new ApiResponse(userService.registerDoctor(doctor), "Login created successfully", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
