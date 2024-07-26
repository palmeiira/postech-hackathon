package br.com.postech.fiap.telemedicine.repository;

import br.com.postech.fiap.telemedicine.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("SELECT a FROM Appointment a WHERE a.doctor.id = :id")
    List<Appointment> findByDoctorId(@Param("id") Long id);

    @Query("SELECT a FROM Appointment a WHERE a.patient.id = :id")
    List<Appointment> findByPatientId(@Param("id") Long id);

}
