package tfm.alzi.services;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import tfm.alzi.models.Pregunta;
import tfm.alzi.models.Usuario;
import tfm.alzi.repositories.PreguntaRepository;
import tfm.alzi.repositories.UsuarioRepository;

@Service
public class PreguntaService {
    
    @Autowired
    private PreguntaRepository preguntaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Pregunta getPreguntaById(long preguntaId){
        return this.preguntaRepository.getById(preguntaId);
    }

    public List<Pregunta> getAllPublicPreguntas(){
        return this.preguntaRepository.findAllPublicPreguntas();
    }

    public List<Pregunta> getMyPrivatePreguntas(Long usuarioId) {
        return this.preguntaRepository.findMyPrivatePreguntas(usuarioId);
    }

    public Boolean soyCreador(Long usuarioId){
        Usuario usuario = this.usuarioRepository.findByDNI(SecurityContextHolder.getContext().getAuthentication().getName());
        return usuarioId == usuario.getId();
    }

    @Transactional
    public void eliminarPregunta(long preguntaId) {
        this.preguntaRepository.deleteById(preguntaId);
    }

    @Transactional
    public void editarPregunta(Pregunta p) {
        this.preguntaRepository.save(p);
    }

    @Transactional
    public void crearPregunta(Pregunta pregunta) {
        this.preguntaRepository.save(pregunta);
    }

}
