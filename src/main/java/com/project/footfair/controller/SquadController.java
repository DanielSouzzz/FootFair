package com.project.footfair.controller;

import com.project.footfair.dto.JoinSquadRequestDTO;
import com.project.footfair.dto.JoinSquadResponseDTO;
import com.project.footfair.dto.SquadInviteResponseDTO;
import com.project.footfair.entity.Squad;
import com.project.footfair.service.SquadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/squad")
@SecurityRequirement(name = "bearer-key")
public class SquadController {

    private final SquadService service;

    public SquadController(SquadService service) {
        this.service = service;
    }

    @Operation(
        summary = "Cria um novo squad",
        description = "Esse endpoint permite criar um novo squad e automaticamente define o criador como administrador."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Squad criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<Squad> createSquad(@Valid @RequestBody Squad squad){
        return ResponseEntity.status(201).body(service.createSquad(squad));
    }

    @Operation(
        summary = "Buscar convite do squad",
        description = "Retorna o convite do squad pelo ID."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Convite retornado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Squad não encontrado")
    })
    @GetMapping("/{id}/invite")
    public ResponseEntity<SquadInviteResponseDTO> getInvite(
        @Parameter(description = "ID do squad", required = true) @PathVariable Long id){
        return ResponseEntity.ok(service.getInvite(id));
    }

    @Operation(
        summary = "Entrar em um squad",
        description = "Permite que um usuário entre em um squad usando o código de convite."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Usuário entrou no squad com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou código incorreto"),
        @ApiResponse(responseCode = "404", description = "Squad não encontrado")
    })
    @PostMapping("/join/{code}")
    public ResponseEntity<JoinSquadResponseDTO> joinSquad(
        @Valid @RequestBody JoinSquadRequestDTO dto,
        @Parameter(description = "Código do convite do squad", required = true) @PathVariable String code) {
        JoinSquadResponseDTO response = service.joinSquad(dto, code);
        return ResponseEntity.status(201).body(response);
    }
}