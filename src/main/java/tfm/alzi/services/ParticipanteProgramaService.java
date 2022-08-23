package tfm.alzi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tfm.alzi.models.ParticipantePrograma;
import tfm.alzi.repositories.ParticipanteProgramaRepository;

@Service
public class ParticipanteProgramaService {

    @Autowired
    private ParticipanteProgramaRepository participanteProgramaRepository;
    
    public List<ParticipantePrograma> getSuscripcionesByID(final Long id){
        return this.participanteProgramaRepository.findByUsuarioID(id);
    }

}
