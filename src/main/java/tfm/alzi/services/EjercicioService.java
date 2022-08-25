package tfm.alzi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import tfm.alzi.models.Ejercicio;
import tfm.alzi.models.Usuario;
import tfm.alzi.repositories.EjercicioRepository;
import tfm.alzi.repositories.InformeEjercicioRepository;
import tfm.alzi.repositories.UsuarioRepository;

@Service
public class EjercicioService {

    @Autowired
    private EjercicioRepository ejercicioRepository;

    @Autowired
    private InformeEjercicioRepository informeEjercicioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    public Ejercicio getEjercicioById(long ejercicioId) {
        return this.ejercicioRepository.getById(ejercicioId);
    }

    public Boolean checkIfFinished(long programaId, long ejercicioId, long usuarioId){
        return this.informeEjercicioRepository.findCustom(programaId, ejercicioId, usuarioId).esFinalizado();
    }

    public List<Ejercicio> getAllPublicEjercicios(){
        return this.ejercicioRepository.findAllPublicEjercicios();
    }

    public List<Ejercicio> getMyPrivateEjercicios(long usuarioId){
        return this.ejercicioRepository.findMyPrivateEjercicios(usuarioId);
    }

    public Boolean soyCreador(long ejercicioUsuarioId){
        Usuario usuario = this.usuarioRepository.findByDNI(SecurityContextHolder.getContext().getAuthentication().getName());
        return ejercicioUsuarioId == usuario.getId();
    }

    @Transactional
    public void crearEjercicio(final Ejercicio ejercicio) {
        this.ejercicioRepository.save(ejercicio);
    }

}
