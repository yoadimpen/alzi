package tfm.alzi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tfm.alzi.models.Pregunta;

@Repository
public interface PreguntaRepository extends JpaRepository<Pregunta, Long> {
    
}
