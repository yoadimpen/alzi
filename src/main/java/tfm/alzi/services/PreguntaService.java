package tfm.alzi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tfm.alzi.models.Pregunta;
import tfm.alzi.repositories.PreguntaRepository;

@Service
public class PreguntaService {
    
    @Autowired
    private PreguntaRepository preguntaRepository;

    public Pregunta getPreguntaById(long preguntaId){
        return this.preguntaRepository.getById(preguntaId);
    }

    public List<Pregunta> getAllPublicPreguntas(){
        return this.preguntaRepository.findAllPublicPreguntas();
    }

}
