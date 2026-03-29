package com.nilemobile.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    private String email;

    private String password;

    private String phoneNumber;

    private boolean isExpired;

    private boolean isLocked;

    private boolean isEnabled;

    @ManyToOne
    @JoinColumn(name="roleId", nullable=false)
    private Role role;

    @OneToOne
    private Customer customer;

    @OneToOne
    private Admin admin;

}
