package br.com.postech.fiap.telemedicine.repository;

import br.com.postech.fiap.telemedicine.entities.User;
import br.com.postech.fiap.telemedicine.enums.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmailAndType(String email, UserType type);

}
