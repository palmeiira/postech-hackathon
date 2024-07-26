package br.com.postech.fiap.telemedicine.entities;

import br.com.postech.fiap.telemedicine.dto.RateDoctorRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    @JsonIgnore
    private Doctor doctor;

    public static Rating fromRequest(RateDoctorRequest rating) {
        Rating r = new Rating();
        r.setRating(rating.getRating());

        return r;
    }
}
