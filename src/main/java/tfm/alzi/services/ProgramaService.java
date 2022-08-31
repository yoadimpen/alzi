package tfm.alzi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import tfm.alzi.models.ParticipantePrograma;
import tfm.alzi.models.Programa;
import tfm.alzi.models.Usuario;
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
            Programa programa = this.programaRepository.getById(p.getProgramaId());
            if(programa.getPublico()){
                res.add(programa);
            }
        }

        return res;
    }

    public List<Programa> getAllProgramas(){
        return this.programaRepository.findAll();
    }

    public List<Programa> getAllPublicProgramas(){
        return this.programaRepository.findAllPublic();
    }

    public List<Programa> getMyPrivateProgramas(long usuarioId){
        return this.programaRepository.findPrivateByUserId(usuarioId);
    }

    public Boolean soyCreador(long programaUsuarioId){
        Usuario usuario = this.usuarioRepository.findByDNI(SecurityContextHolder.getContext().getAuthentication().getName());
        return programaUsuarioId == usuario.getId();
    }

    @Transactional
    public void crearPrograma(final Programa programa) {
        this.programaRepository.save(programa);
    }

    @Transactional
    public void editarPrograma(final Programa programa) {
        this.programaRepository.save(programa);
    }

    @Transactional
    public void eliminarPrograma(long programaId) {
        this.programaRepository.deleteById(programaId);
    }

}
