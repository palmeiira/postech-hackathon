package br.com.postech.fiap.telemedicine.dto;

import br.com.postech.fiap.telemedicine.enums.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequest {

    @Email @NotNull @NotBlank
    private String email;

    @NotNull @NotBlank
    private String password;

    @NotNull
    private UserType type;
}
