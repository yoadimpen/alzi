package tfm.alzi.services;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import tfm.alzi.models.Recordatorio;
import tfm.alzi.repositories.RecordatorioRepository;
import tfm.alzi.repositories.UsuarioRepository;

@Service
public class RecordatorioService {

    @Autowired
    private RecordatorioRepository recordatorioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    public List<Recordatorio> getRecordatoriosByID(final Long id){
        return this.recordatorioRepository.findByParticipanteID(id);
    }

    public void deleteRecordatorio(final Long id) {
        this.recordatorioRepository.deleteById(id);
    }

    @Transactional
    public void crearRecordatorio(final Recordatorio recordatorio) {
        this.recordatorioRepository.save(recordatorio);
    }
    
    @Transactional
    public void editarRecordatorio(final Recordatorio recordatorio) {
        this.recordatorioRepository.save(recordatorio);
    }

    public Recordatorio getRecordatorioById(long recordatorioId) {
        return this.recordatorioRepository.getById(recordatorioId);
    }

    public Recordatorio getMyRecentRecordatorio(){
        return this.recordatorioRepository.getMostRecent(this.usuarioRepository.findByDNI(SecurityContextHolder.getContext().getAuthentication().getName()).getId());
    }

}
