package tfm.alzi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tfm.alzi.models.ParticipantePrograma;
import tfm.alzi.models.ProgramaEjercicio;
import tfm.alzi.repositories.ParticipanteProgramaRepository;
import tfm.alzi.repositories.ProgramaEjercicioRepository;

@Service
public class ProgramaEjercicioService {

    @Autowired
    private ProgramaEjercicioRepository programaEjercicioRepository;
    
    public List<ProgramaEjercicio> getEjerciciosByProgramaID(final Long id){
        return this.programaEjercicioRepository.findByProgramaID(id);
    }

}
