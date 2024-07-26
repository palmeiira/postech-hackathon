package br.com.postech.fiap.telemedicine.dto;

import br.com.postech.fiap.telemedicine.enums.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreatePatientUserRequest {

    @NotBlank(message = "Name is mandatory")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "CPF is mandatory")
    @Size(min = 11, max = 11, message = "CPF must be 11 characters long")
    private String cpf;

    @Email
    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotBlank(message = "Password is mandatory")
    private String password;

    private final UserType type = UserType.PATIENT;

}
