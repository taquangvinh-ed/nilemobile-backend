package com.nilemobile.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Admin extends BaseEntity {

    @Id
    private Long adminId;

    private String firstName;

    private String lastName;

    @OneToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;
}
