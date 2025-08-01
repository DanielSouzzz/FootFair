package com.project.footfair.service;

import com.project.footfair.entity.Squad;
import com.project.footfair.repository.SquadRepository;
import org.springframework.stereotype.Service;

@Service
public class SquadService {
    private final SquadRepository squadRepository;

    public SquadService(SquadRepository squadRepository) {
        this.squadRepository = squadRepository;
    }

    public Squad createSquad(Squad squad){
        Squad newSquad = squadRepository.save(squad);
        return newSquad;
    }
}
