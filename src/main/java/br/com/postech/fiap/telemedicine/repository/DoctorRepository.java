package br.com.postech.fiap.telemedicine.repository;

import br.com.postech.fiap.telemedicine.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    @Query("SELECT d FROM Doctor d " +
            "WHERE (:specialty IS NULL OR d.specialty = :specialty) " +
            "AND (:minRating IS NULL OR (SELECT AVG(r.rating) FROM Rating r WHERE r.doctor = d) > :minRating)")
    List<Doctor> findDoctorsBySpecialtyOrRating(
            @Param("specialty") String specialty,
            @Param("minRating") Double minRating);
}