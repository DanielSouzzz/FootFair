package com.project.footfair.service;

import com.project.footfair.entity.Squad;
import com.project.footfair.repository.SquadRepository;
import org.springframework.stereotype.Service;

@Service
public class SquadService {

    public Squad createSquad(Squad squad){
        Squad newSquad = SquadRepository.save(squad);
    }
}
