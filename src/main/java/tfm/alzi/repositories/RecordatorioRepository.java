package tfm.alzi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tfm.alzi.models.Recordatorio;

@Repository
public interface RecordatorioRepository extends JpaRepository<Recordatorio,Long>{
    
    @Query("SELECT r FROM Recordatorio r WHERE r.usuarioId = ?1")
    List<Recordatorio> findByParticipanteID(Long id);

}
