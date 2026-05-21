package com.babacarmane.studentmanagerbackend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// dto/CoursRequestDTO.java
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoursRequestDTO {

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    private String code;

    private Integer coefficient = 1;
}
