package com.babacarmane.studentmanagerbackend.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// dto/auth/RegisterRequestDTO.java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDTO {
    @NotBlank
    private String nom;
    @Email
    @NotBlank private String email;
    @NotBlank @Size(min = 6) private String password;
}




