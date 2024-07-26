package br.com.postech.fiap.telemedicine.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {

    Long userId;
    String token;
}
