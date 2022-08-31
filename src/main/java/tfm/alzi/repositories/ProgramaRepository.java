package tfm.alzi.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tfm.alzi.models.Programa;

@Repository
public interface ProgramaRepository extends JpaRepository<Programa,Long>{

    @Query("SELECT p FROM Programa p WHERE p.usuarioId = ?1 AND p.publico = false")
    List<Programa> findPrivateByUserId(Long id);

    @Query("SELECT p FROM Programa p WHERE p.publico = true")
    List<Programa> findAllPublic();

}
