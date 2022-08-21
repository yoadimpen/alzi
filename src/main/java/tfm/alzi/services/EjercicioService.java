package tfm.alzi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tfm.alzi.models.Ejercicio;
import tfm.alzi.repositories.EjercicioRepository;

@Service
public class EjercicioService {

    @Autowired
    private EjercicioRepository ejercicioRepository;
    
    public Ejercicio getEjercicioById(long ejercicioId) {
        return this.ejercicioRepository.getById(ejercicioId);
    }

}
