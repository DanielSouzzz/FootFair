package com.project.footfair.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "players")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "squads")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;

    private int role;
    private int speed;
    private int passing;
    private int shooting;
    private int defense;
    private int stamina;

    @Column(unique = true, nullable = false)
    private String token;

    @ManyToMany
    @JoinTable(
            name = "player_squad",
            joinColumns = @JoinColumn(name = "player_id"),
            inverseJoinColumns = @JoinColumn(name = "squad_id")
    )
    private Set<Squad> squads = new HashSet<>();

    @PrePersist
    public void generateToken() {
        if (this.token == null || this.token.isBlank()) {
            this.token = UUID.randomUUID().toString();
        }
    }
}
