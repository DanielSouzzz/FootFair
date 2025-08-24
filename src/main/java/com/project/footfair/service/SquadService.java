package com.project.footfair.service;

import com.project.footfair.dto.SquadInviteResponseDTO;
import com.project.footfair.entity.Player;
import com.project.footfair.entity.Squad;
import com.project.footfair.repository.PlayerRepository;
import com.project.footfair.repository.SquadRepository;
import org.springframework.stereotype.Service;

@Service
public class SquadService extends BaseService{
    private final SquadRepository squadRepository;
    private final PlayerRepository playerRepository;

    public SquadService(SquadRepository squadRepository, PlayerRepository playerRepository) {
        this.squadRepository = squadRepository;
        this.playerRepository = playerRepository;
    }

    public Squad createSquad(Squad squad){
        Long creatorId = squad.getCreate_player_id();
        Player creator = super.findEntityById(playerRepository, creatorId, "Player");

        squad.getPlayers().add(creator); // adiciona o criador na lista de players do squad
        creator.getSquads().add(squad); // populando a relacao ManyToMany do lado do squad

        Squad newSquad = squadRepository.save(squad);
        return newSquad;
    }

    public SquadInviteResponseDTO getInvite(Long squadId) {
        Squad squad = super.findEntityById(squadRepository, squadId, "Squad");

        String baseUrl = "https://peladaequilibrada.com.br/entrar/";
        return new SquadInviteResponseDTO(
                squad.getInvite_code(),
                baseUrl + squad.getInvite_code()
        );
    }
}
