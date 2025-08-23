package com.project.footfair.service;

import com.project.footfair.dto.SquadInviteDTO;
import com.project.footfair.entity.Player;
import com.project.footfair.entity.Squad;
import com.project.footfair.repository.PlayerRepository;
import com.project.footfair.repository.SquadRepository;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;

@Service
public class SquadService {
    private final SquadRepository squadRepository;
    private final PlayerRepository playerRepository;

    public SquadService(SquadRepository squadRepository, PlayerRepository playerRepository) {
        this.squadRepository = squadRepository;
        this.playerRepository = playerRepository;
    }

    public Squad createSquad(Squad squad){
        Player creator = playerRepository.findById(squad.getCreate_player_id())
                .orElseThrow(()-> new ValidationException("User not found!"));

        squad.getPlayers().add(creator); // adiciona o criador na lista de players do squad
        creator.getSquads().add(squad); // populando a relacao ManyToMany do lado do squad

        Squad newSquad = squadRepository.save(squad);
        return newSquad;
    }

    public SquadInviteDTO getInvite(Long squadId) {
        Squad squad = squadRepository.findById(squadId)
                .orElseThrow(() -> new ValidationException("Squad não encontrado"));

        String baseUrl = "https://peladaequilibrada.com/entrar/";
        return new SquadInviteDTO(
                squad.getInvite_code(),
                baseUrl + squad.getInvite_code()
        );
    }
}
