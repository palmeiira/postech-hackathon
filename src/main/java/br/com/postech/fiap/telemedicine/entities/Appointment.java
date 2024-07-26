package br.com.postech.fiap.telemedicine.entities;

import br.com.postech.fiap.telemedicine.enums.AppointmentStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Doctor doctor;

    @ManyToOne
    private Patient patient;

    @OneToOne
    @JsonIgnore
    private Slot slot;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    private String meetingLink;

    private LocalDateTime getSchedule() {
        if (slot != null) {
            return LocalDateTime.of(slot.getDate(), slot.getStartTime());
        } else {
            return null;
        }
    }
}

