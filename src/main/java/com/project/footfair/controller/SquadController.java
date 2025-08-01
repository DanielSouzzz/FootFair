package com.project.footfair.controller;

import com.project.footfair.entity.Squad;
import com.project.footfair.service.SquadService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

// criar grupo, convidar
@Controller
@RequestMapping("/api/squad")
public class SquadController {

    private final SquadService service;

    public SquadController(SquadService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Squad> createSquad(@Valid @RequestBody Squad squad){
        return ResponseEntity.status(201).body(service.createSquad(squad));
    }
}