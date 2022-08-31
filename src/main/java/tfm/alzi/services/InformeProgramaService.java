package tfm.alzi.services;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import tfm.alzi.models.InformePrograma;
import tfm.alzi.models.Usuario;
import tfm.alzi.repositories.InformeProgramaRepository;
import tfm.alzi.repositories.UsuarioRepository;

@Service
public class InformeProgramaService {

    @Autowired
    private InformeProgramaRepository informeProgramaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public InformePrograma getInformeProgramaCustom(long programaId, long usuarioId){
        return informeProgramaRepository.findCustom(programaId, usuarioId);
    }

    @Transactional
    public void editarInformePrograma(final InformePrograma informePrograma) {
        this.informeProgramaRepository.save(informePrograma);
    }

    @Transactional
    public void eliminarLista(List<InformePrograma> ls) {
        this.informeProgramaRepository.deleteAll(ls);
    }

    public List<InformePrograma> findByProgramaId(long programaId){
        return this.informeProgramaRepository.findByProgramaId(programaId);
    }

    public Integer getMyProgramProgress(long programaId){
        Usuario usuario = this.usuarioRepository.findByDNI(SecurityContextHolder.getContext().getAuthentication().getName());
        return this.getInformeProgramaCustom(programaId, usuario.getId()).getProgreso();
    }

    public Integer getProgramProgressByUserId(long programaId, long usuarioId){
        return this.getInformeProgramaCustom(programaId, usuarioId).getProgreso();
    }

    @Transactional
    public void crearInforme(InformePrograma ipro) {
        this.informeProgramaRepository.save(ipro);
    }
    
}
