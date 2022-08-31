package tfm.alzi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tfm.alzi.models.InformePrograma;

public interface InformeProgramaRepository extends JpaRepository<InformePrograma, Long> {
    
    @Query("SELECT ip FROM InformePrograma ip WHERE ip.programaId = ?1 AND ip.usuarioId = ?2")
    InformePrograma findCustom(Long programaId, Long usuarioId);

    @Query("SELECT ip FROM InformePrograma ip WHERE ip.programaId = ?1")
    List<InformePrograma> findByProgramaId(long programaId);

}
