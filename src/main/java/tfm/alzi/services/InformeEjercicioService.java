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

    @Transactional
    public void eliminarLista(List<InformeEjercicio> ls) {
        this.informeEjercicioRepository.deleteAll(ls);
    }

    public List<InformeEjercicio> findByProgramaId(long programaId){
        return this.informeEjercicioRepository.findByProgramaId(programaId);
    }

    public List<InformeEjercicio> findByProgramaIdEjercicioId(long programaId, long ejercicioId) {
        return this.informeEjercicioRepository.findByProgramaIdEjercicioId(programaId, ejercicioId);
    }

}
