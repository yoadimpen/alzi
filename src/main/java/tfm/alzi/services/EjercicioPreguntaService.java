package tfm.alzi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tfm.alzi.models.EjercicioPregunta;
import tfm.alzi.repositories.EjercicioPreguntaRepository;

@Service
public class EjercicioPreguntaService {
    
    @Autowired
    private EjercicioPreguntaRepository ejercicioPreguntaRepository;

    public List<EjercicioPregunta> getEjercicioPreguntaByEjercicioId(long ejercicioId) {
        return this.ejercicioPreguntaRepository.findByEjercicioId(ejercicioId);
    }

}
