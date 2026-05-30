package com.babacarmane.studentmanagerbackend.entities;

import com.babacarmane.studentmanagerbackend.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {
// ↑ UserDetails = interface Spring Security
//   oblige à implémenter getUsername(), getPassword()...

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;
    // ↑ stocké hashé — jamais en clair en base

    @Enumerated(EnumType.STRING)
    private Role role;

    // ── Méthodes UserDetails ──────────────────

    @Override
    public String getUsername() {
        return email;
        // ↑ Spring Security utilise l'email comme identifiant
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
        // ↑ retourne le rôle : "ROLE_USER" ou "ROLE_ADMIN"
    }

    @Override
    public boolean isAccountNonExpired()  { return true; }

    @Override
    public boolean isAccountNonLocked()   { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled()            { return true; }
}
