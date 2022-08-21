package tfm.alzi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tfm.alzi.models.Informe;
import tfm.alzi.repositories.InformeRepository;

@Service
public class InformeService {

    @Autowired
    private InformeRepository informeRepository;
    
    public List<Informe> getInformesByUsuarioId(long usuarioId) {
        return this.informeRepository.findByUsuarioId(usuarioId);
    }

}
