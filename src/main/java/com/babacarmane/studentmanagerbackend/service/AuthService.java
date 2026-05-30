package com.babacarmane.studentmanagerbackend.service;

import com.babacarmane.studentmanagerbackend.dto.auth.AuthResponseDTO;
import com.babacarmane.studentmanagerbackend.dto.auth.LoginRequestDTO;
import com.babacarmane.studentmanagerbackend.dto.auth.RegisterRequestDTO;
import com.babacarmane.studentmanagerbackend.entities.User;
import com.babacarmane.studentmanagerbackend.enums.Role;
import com.babacarmane.studentmanagerbackend.exception.EtudiantNotFoundException;
import com.babacarmane.studentmanagerbackend.exception.InvalidCredentialsException;
import com.babacarmane.studentmanagerbackend.repository.UserRepository;
import com.babacarmane.studentmanagerbackend.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// service/AuthService.java
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;


    public AuthResponseDTO register(RegisterRequestDTO dto) {

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email déjà utilisé");
        }

        User user = User.builder()
                .nom(dto.getNom())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                // ↑ encode le mot de passe avant de le stocker
                .role(Role.USER)
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(user);

        return AuthResponseDTO.builder()
                .token(token)
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }


    // service/AuthService.java
    public AuthResponseDTO login(LoginRequestDTO dto) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            dto.getEmail(),
                            dto.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            // ↑ Spring lance cette exception si email ou password incorrect
            //   on la remplace par notre exception métier propre
            throw new InvalidCredentialsException();
        }

        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new EtudiantNotFoundException(0L));

        String token = jwtService.generateToken(user);

        return AuthResponseDTO.builder()
                .token(token)
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}
