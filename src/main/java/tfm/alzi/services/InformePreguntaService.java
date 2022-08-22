package tfm.alzi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import tfm.alzi.models.InformePregunta;
import tfm.alzi.repositories.InformePreguntaRepository;

@Service
public class InformePreguntaService {
 
    @Autowired
    private InformePreguntaRepository informePreguntaRepository;

    public InformePregunta getInformePreguntaById(long informePreguntaId){
        return this.informePreguntaRepository.getById(informePreguntaId);
    }

    public List<InformePregunta> getInformesPreguntaCustom(long programaId, long ejercicioId, long usuarioId){
        return this.informePreguntaRepository.findCustom(programaId, ejercicioId, usuarioId);
    }

    @Transactional
    public void editarInformePregunta(final InformePregunta informePregunta) {
        this.informePreguntaRepository.save(informePregunta);
    }

}
