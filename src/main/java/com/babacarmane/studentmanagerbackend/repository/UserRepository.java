package com.babacarmane.studentmanagerbackend.repository;


import com.babacarmane.studentmanagerbackend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    // ↑ Spring Security cherche l'utilisateur par email
    //   au moment du login

    boolean existsByEmail(String email);
    // ↑ vérifie si l'email est déjà pris à l'inscription
}
