package br.com.postech.fiap.telemedicine.entities;

import br.com.postech.fiap.telemedicine.enums.UserType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true)
    private String email;
    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    private UserType type;
}

