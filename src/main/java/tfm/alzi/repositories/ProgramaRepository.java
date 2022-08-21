package tfm.alzi.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tfm.alzi.models.Programa;

@Repository
public interface ProgramaRepository extends JpaRepository<Programa,Long>{

}
