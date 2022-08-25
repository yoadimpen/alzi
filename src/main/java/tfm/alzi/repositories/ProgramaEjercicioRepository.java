package tfm.alzi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tfm.alzi.models.ProgramaEjercicio;

@Repository
public interface ProgramaEjercicioRepository extends JpaRepository<ProgramaEjercicio,Long>{
    
    @Query("SELECT pe FROM ProgramaEjercicio pe WHERE pe.programaId = ?1")
    List<ProgramaEjercicio> findByProgramaID(Long id);

    @Query("SELECT pe FROM ProgramaEjercicio pe WHERE pe.programaId = ?1 AND pe.ejercicioId = ?2")
    ProgramaEjercicio findByProgramaIdEjercicioId(long programaId, long ejercicioId);

}
