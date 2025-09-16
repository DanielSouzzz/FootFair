package com.project.footfair.controller;

import com.project.footfair.dto.JoinSquadRequestDTO;
import com.project.footfair.dto.JoinSquadResponseDTO;
import com.project.footfair.dto.SquadInviteResponseDTO;
import com.project.footfair.entity.Squad;
import com.project.footfair.service.SquadService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// criar grupo, convidar
@RestController
@RequestMapping("/api/squad")
@SecurityRequirement(name = "bearer-key")
public class SquadController {

    private final SquadService service;

    public SquadController(SquadService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Squad> createSquad(@Valid @RequestBody Squad squad){
        return ResponseEntity.status(201).body(service.createSquad(squad));
    }

    @GetMapping("/{id}/invite")
    public ResponseEntity<SquadInviteResponseDTO> getInvite(@PathVariable Long id){
        return ResponseEntity.ok(service.getInvite(id));
    }

    @PostMapping("/join/{code}")
    public ResponseEntity<JoinSquadResponseDTO> joinSquad(@Valid @RequestBody JoinSquadRequestDTO dto,
                                                          @PathVariable String code) {
        JoinSquadResponseDTO response = service.joinSquad(dto, code);
        return ResponseEntity.status(201).body(response);
    }
}