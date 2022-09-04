package tfm.alzi.integration.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import tfm.alzi.models.Pregunta;
import tfm.alzi.repositories.PreguntaRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PreguntaRepositoryIntegrationTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PreguntaRepository preguntaRepository;

    @Test
    public void shouldFindAllPublicPreguntas() {

        List<Pregunta> found = preguntaRepository.findAllPublicPreguntas();

        assertEquals(12, found.size());
    }

    @WithMockUser(username = "Y1234569W", authorities = {"ESPECIALISTA"})
    @Test
    public void shouldFindAllPrivatePreguntas() {

        List<Pregunta> found = preguntaRepository.findMyPrivatePreguntas(Long.valueOf("10003"));

        assertEquals(39, found.size());
    }
    
}
