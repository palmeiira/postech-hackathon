package br.com.postech.fiap.telemedicine.repository;

import br.com.postech.fiap.telemedicine.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
