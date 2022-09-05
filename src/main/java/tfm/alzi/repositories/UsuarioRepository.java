package tfm.alzi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tfm.alzi.models.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long>{
    
    @Query("SELECT u FROM Usuario u WHERE u.dni = ?1")
    Usuario findByDNI(String dni);

    @Query("SELECT COUNT(*) FROM Usuario WHERE dni= ?1")
    long numUsuariosByDNI(String dni);

    @Query("SELECT COUNT(*) FROM Usuario WHERE email= ?1")
    long numUsuariosByEmail(String email);

    @Query("SELECT u FROM Usuario u WHERE u.roles = 'CUIDADOR'")
    List<Usuario> findAllCuidadores();

    @Query("SELECT u FROM Usuario u WHERE u.roles = 'USUARIO'")
    List<Usuario> findAllUsuarios();

    @Query("SELECT u FROM Usuario u WHERE u.roles = 'ESPECIALISTA'")
    List<Usuario> findAllEspecialistas();

}
