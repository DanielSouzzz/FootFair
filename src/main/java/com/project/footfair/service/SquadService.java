package com.project.footfair.service;

import com.project.footfair.dto.JoinSquadRequestDTO;
import com.project.footfair.dto.JoinSquadResponseDTO;
import com.project.footfair.dto.SquadInviteResponseDTO;
import com.project.footfair.entity.Player;
import com.project.footfair.entity.Squad;
import com.project.footfair.repository.PlayerRepository;
import com.project.footfair.repository.SquadRepository;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SquadService extends BaseService{
    private final PlayerService playerService;
    private final SquadRepository squadRepository;
    private final PlayerRepository playerRepository;

    public SquadService(PlayerService playerService, SquadRepository squadRepository, PlayerRepository playerRepository) {
        this.playerService = playerService;
        this.squadRepository = squadRepository;
        this.playerRepository = playerRepository;
    }

    @Transactional
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

    @Transactional
    public JoinSquadResponseDTO joinSquad(@Valid JoinSquadRequestDTO dto, String code) {
        Squad squad = squadRepository.findInviteCode(code)
                .orElseThrow(() -> new ValidationException("Invalid invite code!"));

        Player player = playerRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new ValidationException("Player not found!"));

        Long exists = playerRepository.findPlayerInSquadById(player.getId());
        boolean isPlayerInSquad = exists == 1L;

        if(isPlayerInSquad){
            return new JoinSquadResponseDTO(null, null, "O player ja faz parte do squad");
        }

        Player newPlayer = playerService.createPlayer(player);

        var responseDTO = new JoinSquadResponseDTO();

        playerRepository.joinPlayerInSquad(newPlayer.getId(), squad.getId());

        responseDTO.setDataPlayer(newPlayer);
        responseDTO.setDataSquad(squad);

        return responseDTO;
    }
}
