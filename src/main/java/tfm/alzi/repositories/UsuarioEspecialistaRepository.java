package tfm.alzi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tfm.alzi.models.UsuarioEspecialista;

@Repository
public interface UsuarioEspecialistaRepository extends JpaRepository<UsuarioEspecialista, Long> {
    
    @Query("SELECT ue FROM UsuarioEspecialista ue WHERE ue.especialistaId = ?1")
    List<UsuarioEspecialista> findByEspecialistaId(Long especialistaId);

}
