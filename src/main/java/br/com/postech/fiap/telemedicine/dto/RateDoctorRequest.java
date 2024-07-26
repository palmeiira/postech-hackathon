package br.com.postech.fiap.telemedicine.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class RateDoctorRequest {
    @Min(value = 0, message = "Minimum value for rating is 0")
    @Max(value = 5, message = "Maximum value for rating is 5")
    Double rating;
}
