package tfm.alzi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tfm.alzi.models.Pregunta;

@Repository
public interface PreguntaRepository extends JpaRepository<Pregunta, Long> {
    
    @Query("SELECT p FROM Pregunta p WHERE p.publico = true")
    List<Pregunta> findAllPublicPreguntas();

    @Query("SELECT p FROM Pregunta p WHERE p.publico = false AND p.usuarioId = ?1")
    List<Pregunta> findMyPrivatePreguntas(Long usuarioId);

}
