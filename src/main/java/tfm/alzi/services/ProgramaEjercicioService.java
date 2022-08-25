package tfm.alzi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import tfm.alzi.models.ProgramaEjercicio;
import tfm.alzi.repositories.ProgramaEjercicioRepository;

@Service
public class ProgramaEjercicioService {

    @Autowired
    private ProgramaEjercicioRepository programaEjercicioRepository;
    
    public List<ProgramaEjercicio> getEjerciciosByProgramaID(final Long id){
        return this.programaEjercicioRepository.findByProgramaID(id);
    }

    @Transactional
    public void crearRelacion(final ProgramaEjercicio programaEjercicio) {
        this.programaEjercicioRepository.save(programaEjercicio);
    }

    @Transactional
    public void eliminarLista(List<ProgramaEjercicio> ls) {
        this.programaEjercicioRepository.deleteAll(ls);;
    }

}
