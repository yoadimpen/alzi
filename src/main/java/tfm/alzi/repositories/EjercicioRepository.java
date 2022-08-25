package tfm.alzi.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tfm.alzi.models.Ejercicio;

@Repository
public interface EjercicioRepository extends JpaRepository<Ejercicio,Long>{

    @Query("SELECT e FROM Ejercicio e WHERE e.publico = true")
    List<Ejercicio> findAllPublicEjercicios();

}
