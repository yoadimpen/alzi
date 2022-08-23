package tfm.alzi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import tfm.alzi.models.ParticipantePrograma;
import tfm.alzi.models.Programa;
import tfm.alzi.repositories.ParticipanteProgramaRepository;
import tfm.alzi.repositories.ProgramaRepository;
import tfm.alzi.repositories.UsuarioRepository;

@Service
public class ProgramaService {

    @Autowired
    private ProgramaRepository programaRepository;

    @Autowired
    private ParticipanteProgramaRepository participanteProgramaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    public Programa getProgramaById(long programaId) {
        return this.programaRepository.getById(programaId);
    }

    public List<Programa> getMyProgramas(){
        List<Programa> res = new ArrayList<Programa>();

        List<ParticipantePrograma> ls = this.participanteProgramaRepository.findByUsuarioID((this.usuarioRepository.findByDNI(SecurityContextHolder.getContext().getAuthentication().getName()).getId()));

        for (ParticipantePrograma p:ls){
            res.add(this.programaRepository.getById(p.getProgramaId()));
        }

        return res;
    }

    public List<Programa> getAllProgramas(){
        return this.programaRepository.findAll();
    }

}
