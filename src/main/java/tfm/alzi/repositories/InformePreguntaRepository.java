package tfm.alzi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tfm.alzi.models.InformePregunta;

@Repository
public interface InformePreguntaRepository extends JpaRepository<InformePregunta,Long>{
    
    @Query("SELECT ip FROM InformePregunta ip WHERE ip.programaId = ?1 AND ip.ejercicioId = ?2 AND ip.usuarioId = ?3")
    List<InformePregunta> findCustom(Long programaId, Long ejercicioId, Long usuarioId);

    @Query("SELECT ip FROM InformePregunta ip WHERE ip.programaId = ?1")
    List<InformePregunta> findByProgramaId(long programaId);
    
    @Query("SELECT ip FROM InformePregunta ip WHERE ip.programaId = ?1 AND ip.ejercicioId = ?2")
    List<InformePregunta> findByProgramaIdEjercicioId(long programaId, long ejercicioId);

    @Query("SELECT ip FROM InformePregunta ip WHERE ip.ejercicioId = ?1")
    List<InformePregunta> findByEjercicioId(long ejercicioId);

}
