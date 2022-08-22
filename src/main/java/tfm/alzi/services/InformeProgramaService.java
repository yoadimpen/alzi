package tfm.alzi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import tfm.alzi.models.InformePrograma;
import tfm.alzi.repositories.InformeProgramaRepository;

@Service
public class InformeProgramaService {

    @Autowired
    private InformeProgramaRepository informeProgramaRepository;

    public InformePrograma getInformeProgramaCustom(long programaId, long usuarioId){
        return informeProgramaRepository.findCustom(programaId, usuarioId);
    }

    @Transactional
    public void editarInformePrograma(final InformePrograma informePrograma) {
        this.informeProgramaRepository.save(informePrograma);
    }
    
}
