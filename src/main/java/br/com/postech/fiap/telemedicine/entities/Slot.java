package br.com.postech.fiap.telemedicine.entities;

import br.com.postech.fiap.telemedicine.enums.SlotStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
public class Slot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private Double price;
    @Enumerated(EnumType.STRING)
    private SlotStatus status;

}
