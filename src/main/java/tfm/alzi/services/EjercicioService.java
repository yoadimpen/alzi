package tfm.alzi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tfm.alzi.models.Ejercicio;
import tfm.alzi.repositories.EjercicioRepository;
import tfm.alzi.repositories.InformeEjercicioRepository;

@Service
public class EjercicioService {

    @Autowired
    private EjercicioRepository ejercicioRepository;

    @Autowired
    private InformeEjercicioRepository informeEjercicioRepository;
    
    public Ejercicio getEjercicioById(long ejercicioId) {
        return this.ejercicioRepository.getById(ejercicioId);
    }

    public Boolean checkIfFinished(long programaId, long ejercicioId, long usuarioId){
        return this.informeEjercicioRepository.findCustom(programaId, ejercicioId, usuarioId).esFinalizado();
    }

    public List<Ejercicio> getAllPublicEjercicios(){
        return this.ejercicioRepository.findAllPublicEjercicios();
    }

}
