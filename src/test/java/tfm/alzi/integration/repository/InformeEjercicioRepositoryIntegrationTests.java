package tfm.alzi.integration.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import tfm.alzi.models.InformeEjercicio;
import tfm.alzi.repositories.InformeEjercicioRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class InformeEjercicioRepositoryIntegrationTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private InformeEjercicioRepository informeEjercicioRepository;

    @Test
    public void shouldFindAllByExerciseID() {

        List<InformeEjercicio> found = informeEjercicioRepository.findByEjercicioId(Long.valueOf("10002"));

        assertEquals(1, found.size());
    }

    @Test
    public void shouldFindAllByProgramIDExerciseID() {

        List<InformeEjercicio> found = informeEjercicioRepository.findByProgramaIdEjercicioId(Long.valueOf("10002"), Long.valueOf("10002"));

        assertEquals(1, found.size());
    }

    @Test
    public void shouldFindAllByProgramID() {

        List<InformeEjercicio> found = informeEjercicioRepository.findByProgramaId(Long.valueOf("10002"));

        assertEquals(2, found.size());
    }

    @Test
    public void shouldFindAllByProgramIDUserID() {

        List<InformeEjercicio> found = informeEjercicioRepository.findInformesEjerciciosByProgramaUsuario(Long.valueOf("10002"), Long.valueOf("10002"));

        assertEquals(2, found.size());
    }

    @Test
    public void shouldFindAllByProgramIDExerciseIDUserID() {

        InformeEjercicio found = informeEjercicioRepository.findCustom(Long.valueOf("10002"), Long.valueOf("10002"), Long.valueOf("10002"));

        assertEquals(10002, found.getId());
    }
    
}
