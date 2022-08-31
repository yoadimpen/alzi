package tfm.alzi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tfm.alzi.models.EjercicioPregunta;

@Repository
public interface EjercicioPreguntaRepository extends JpaRepository<EjercicioPregunta,Long>{

    @Query("SELECT ep FROM EjercicioPregunta ep WHERE ep.ejercicioId = ?1")
    List<EjercicioPregunta> findByEjercicioId(Long ejercicioId);

    @Query("SELECT ep FROM EjercicioPregunta ep WHERE ep.ejercicioId = ?1 AND ep.preguntaId = ?2")
    EjercicioPregunta findByEjercicioIdPreguntaId(long ejercicioId, Long preguntaId);

    @Query("SELECT ep FROM EjercicioPregunta ep WHERE ep.preguntaId = ?1")
    List<EjercicioPregunta> findByPreguntaId(long preguntaId);

}
