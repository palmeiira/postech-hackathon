package br.com.postech.fiap.telemedicine.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse {
    Object data;
    String message;
    Boolean success;
}
