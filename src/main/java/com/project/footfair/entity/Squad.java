package com.project.footfair.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "squads")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Squad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long code;

    @NotBlank(message = "Name is mandatory.")
    private String name;

    @ManyToOne
    @JoinColumn(name = "create_player_id")
    private Player creator;

    @ManyToMany(mappedBy = "squads")
    private Set<Player> players = new HashSet<>();
}
