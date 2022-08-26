package tfm.alzi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import tfm.alzi.models.EjercicioPregunta;
import tfm.alzi.repositories.EjercicioPreguntaRepository;

@Service
public class EjercicioPreguntaService {
    
    @Autowired
    private EjercicioPreguntaRepository ejercicioPreguntaRepository;

    public List<EjercicioPregunta> getEjercicioPreguntaByEjercicioId(long ejercicioId) {
        return this.ejercicioPreguntaRepository.findByEjercicioId(ejercicioId);
    }

    @Transactional
    public void crearRelacion(final EjercicioPregunta ejercicioPregunta) {
        this.ejercicioPreguntaRepository.save(ejercicioPregunta);
    }

    @Transactional
    public void eliminarLista(List<EjercicioPregunta> ejercicioPreguntas) {
        this.ejercicioPreguntaRepository.deleteAll(ejercicioPreguntas);
    }

    public List<EjercicioPregunta> findByEjercicioId(long ejercicioId) {
        return this.ejercicioPreguntaRepository.findByEjercicioId(ejercicioId);
    }

    public EjercicioPregunta findByEjercicioIdPreguntaId(long ejercicioId, Long preguntaId) {
        return this.ejercicioPreguntaRepository.findByEjercicioIdPreguntaId(ejercicioId,preguntaId);
    }

    @Transactional
    public void eliminarRelacion(EjercicioPregunta rel) {
        this.ejercicioPreguntaRepository.delete(rel);
    }

}
