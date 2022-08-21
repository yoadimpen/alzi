package tfm.alzi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tfm.alzi.models.Informe;

@Repository
public interface InformeRepository extends JpaRepository<Informe,Long>{
    
    @Query("SELECT i FROM Informe i WHERE i.usuarioId = ?1")
    List<Informe> findByUsuarioId(Long id);

}
