package com.auth.userAuthentication.data.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Setter
@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class SecurityDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Length(max = 500)
    private String token;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private AppUser user;

    private boolean isRevoked;

    private boolean isExpired;
}
