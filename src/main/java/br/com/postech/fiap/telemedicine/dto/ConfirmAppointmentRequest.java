package br.com.postech.fiap.telemedicine.dto;

import br.com.postech.fiap.telemedicine.enums.AppointmentStatus;
import lombok.Data;

@Data
public class ConfirmAppointmentRequest {
    AppointmentStatus status;
}
