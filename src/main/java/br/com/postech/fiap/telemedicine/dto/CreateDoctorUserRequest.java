package br.com.postech.fiap.telemedicine.dto;

import br.com.postech.fiap.telemedicine.enums.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateDoctorUserRequest {

    @NotBlank(message = "Name is mandatory")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "CRM is mandatory")
    @Size(min = 6, max = 20, message = "Name must be between 6 and 20 characters")
    private String crm;
    @NotBlank(message = "Specialty is mandatory")
    private String specialty;
    @Email(message = "Invalid mail format.")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotBlank(message = "Password is mandatory")
    private String password;

    private final UserType type = UserType.DOCTOR;

}
