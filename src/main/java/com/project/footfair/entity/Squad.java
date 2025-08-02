package com.project.footfair.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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

    @Column(nullable = false, unique = true, updatable = false)
    private String invite_code;

    @NotBlank(message = "Name is mandatory.")
    private String name;

    @NotNull(message = "userId is mandatory.")
    private Long create_player_id;

    @ManyToMany(mappedBy = "squads")
    @JsonIgnore
    private Set<Player> players = new HashSet<>();

    @PrePersist
    public void generateCode(){
        this.invite_code = UUID.randomUUID().toString();
    }
}
