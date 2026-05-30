package com.babacarmane.studentmanagerbackend.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO {
    private String token;
    private String email;
    private String role;
    // ↑ ce que le client reçoit après login réussi
}
