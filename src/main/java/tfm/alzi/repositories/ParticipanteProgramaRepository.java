package tfm.alzi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tfm.alzi.models.ParticipantePrograma;

@Repository
public interface ParticipanteProgramaRepository extends JpaRepository<ParticipantePrograma,Long>{
    
    @Query("SELECT pp FROM ParticipantePrograma pp WHERE pp.usuarioId = ?1")
    List<ParticipantePrograma> findByUsuarioID(Long id);

    @Query("SELECT pp FROM ParticipantePrograma pp WHERE pp.programaId = ?1")
    List<ParticipantePrograma> findByProgramaId(Long programaId);

}
