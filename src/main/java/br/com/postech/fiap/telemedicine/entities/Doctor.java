package br.com.postech.fiap.telemedicine.entities;

import br.com.postech.fiap.telemedicine.dto.CreateDoctorUserRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.List;

@Entity
@Data
public class Doctor extends User {

    @Column(unique = true)
    private String crm;
    private String specialty;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Slot> slots;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Rating> ratings;

    public static Doctor fromRequest(CreateDoctorUserRequest doctor) {
        Doctor d = new Doctor();
        d.setName(doctor.getName());
//        d.setPassword(doctor.getPassword());
        d.setType(doctor.getType());
        d.setCrm(doctor.getCrm());
        d.setEmail(doctor.getEmail());
        d.setSpecialty(doctor.getSpecialty());
        d.setSlots(null);
        d.setRatings(null);

        return d;
    }

    public void addSlot(Slot slot) {
        slots.add(slot);
    }

    public void removeSlot(Slot slot) {
        slots.remove(slot);
    }

    public void addRating(Rating r) {
        ratings.add(r);
    }
}
