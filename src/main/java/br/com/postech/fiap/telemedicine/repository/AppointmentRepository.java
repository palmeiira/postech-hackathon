package br.com.postech.fiap.telemedicine.repository;

import br.com.postech.fiap.telemedicine.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
