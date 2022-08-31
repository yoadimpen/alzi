package tfm.alzi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import tfm.alzi.models.UsuarioCuidador;
import tfm.alzi.repositories.UsuarioCuidadorRepository;

@Service
public class UsuarioCuidadorService {

    @Autowired
    private UsuarioCuidadorRepository usuarioCuidadorRepository;

    public UsuarioCuidador findByUsuarioId(final Long usuarioId){
        return this.usuarioCuidadorRepository.findByUsuarioId(usuarioId);
    }

    @Transactional
    public void crearRel(UsuarioCuidador uc) {
        this.usuarioCuidadorRepository.save(uc);
    }

}
