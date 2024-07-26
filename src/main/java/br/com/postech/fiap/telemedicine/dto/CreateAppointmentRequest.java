package br.com.postech.fiap.telemedicine.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateAppointmentRequest {
    @NotNull
    Long doctorId;
    @NotNull
    Long patientId;
    @NotNull
    Long slotId;
}
