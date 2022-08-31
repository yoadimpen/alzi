package tfm.alzi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import tfm.alzi.models.ParticipantePrograma;
import tfm.alzi.repositories.ParticipanteProgramaRepository;

@Service
public class ParticipanteProgramaService {

    @Autowired
    private ParticipanteProgramaRepository participanteProgramaRepository;
    
    public List<ParticipantePrograma> getSuscripcionesByID(final Long id){
        return this.participanteProgramaRepository.findByUsuarioID(id);
    }

    public List<ParticipantePrograma> findByProgramaId(final Long programaId){
        return this.participanteProgramaRepository.findByProgramaId(programaId);
    }

    @Transactional
    public void eliminarLista(List<ParticipantePrograma> ls) {
        this.participanteProgramaRepository.deleteAll(ls);
    }

    @Transactional
    public void crearRel(ParticipantePrograma pp) {
        this.participanteProgramaRepository.save(pp);
    }

}
