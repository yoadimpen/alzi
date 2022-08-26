package tfm.alzi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tfm.alzi.models.InformeEjercicio;

public interface InformeEjercicioRepository extends JpaRepository<InformeEjercicio,Long>{
    
    @Query("SELECT ie FROM InformeEjercicio ie WHERE ie.programaId = ?1 AND ie.ejercicioId = ?2 AND ie.usuarioId = ?3")
    InformeEjercicio findCustom(Long programaId, Long ejercicioId, Long usuarioId);

    @Query("SELECT ie FROM InformeEjercicio ie WHERE ie.programaId = ?1 AND ie.usuarioId = ?2")
    List<InformeEjercicio> findInformesEjerciciosByProgramaUsuario(Long programaId, Long usuarioId);

    @Query("SELECT ie FROM InformeEjercicio ie WHERE ie.programaId = ?1")
    List<InformeEjercicio> findByProgramaId(long programaId);

    @Query("SELECT ie FROM InformeEjercicio ie WHERE ie.programaId = ?1 AND ie.ejercicioId = ?2")
    List<InformeEjercicio> findByProgramaIdEjercicioId(long programaId, long ejercicioId);

    @Query("SELECT ie FROM InformeEjercicio ie WHERE ie.ejercicioId = ?1")
    List<InformeEjercicio> findByEjercicioId(long ejercicioId);

}
