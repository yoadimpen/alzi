package tfm.alzi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import tfm.alzi.models.InformeEjercicio;
import tfm.alzi.repositories.InformeEjercicioRepository;

@Service
public class InformeEjercicioService {
    
    @Autowired
    private InformeEjercicioRepository informeEjercicioRepository;

    public InformeEjercicio getInformeEjercicioCustom(long programaId, long ejercicioId, long usuarioId){
        return this.informeEjercicioRepository.findCustom(programaId, ejercicioId, usuarioId);
    }

    public List<InformeEjercicio> getInformesEjercicioCustom(long programaId, long usuarioId){
        return this.informeEjercicioRepository.findInformesEjerciciosByProgramaUsuario(programaId, usuarioId);
    }

    @Transactional
    public void editarInformeEjercicio(final InformeEjercicio informeEjercicio) {
        this.informeEjercicioRepository.save(informeEjercicio);
    }

}
