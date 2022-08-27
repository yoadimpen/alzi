package tfm.alzi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tfm.alzi.models.UsuarioCuidador;

@Repository
public interface UsuarioCuidadorRepository extends JpaRepository<UsuarioCuidador,Long>{

    @Query("SELECT uc FROM UsuarioCuidador uc WHERE uc.cuidadorId = ?1")
    List<UsuarioCuidador> findByCuidadorId(Long cuidadorId);

    @Query("SELECT uc FROM UsuarioCuidador uc WHERE uc.usuarioId = ?1")
    UsuarioCuidador findByUsuarioId(Long usuarioId);
    
}
