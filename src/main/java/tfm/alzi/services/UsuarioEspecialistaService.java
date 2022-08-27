package tfm.alzi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import tfm.alzi.models.UsuarioEspecialista;
import tfm.alzi.repositories.UsuarioEspecialistaRepository;

@Service
public class UsuarioEspecialistaService {

    @Autowired
    private UsuarioEspecialistaRepository usuarioEspecialistaRepository;

    @Transactional
    public void crearRel(UsuarioEspecialista ue) {
        this.usuarioEspecialistaRepository.save(ue);
    }

}
