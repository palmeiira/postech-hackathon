package br.com.postech.fiap.telemedicine.entities;

import br.com.postech.fiap.telemedicine.dto.CreatePatientUserRequest;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Patient extends User {
    private String cpf;

    public static Patient fromRequest(CreatePatientUserRequest patient) {
        Patient p = new Patient();
        p.setName(patient.getName());
        p.setCpf(patient.getCpf());
        p.setEmail(patient.getEmail());
        p.setType(patient.getType());

        return p;
    }
}
