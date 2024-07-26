package br.com.postech.fiap.telemedicine.repository;

import br.com.postech.fiap.telemedicine.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Long> {
}