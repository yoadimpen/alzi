package tfm.alzi.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tfm.alzi.models.Ejercicio;

@Repository
public interface EjercicioRepository extends JpaRepository<Ejercicio,Long>{

}
