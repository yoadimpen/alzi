package tfm.alzi.integration.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import tfm.alzi.models.Recordatorio;
import tfm.alzi.repositories.RecordatorioRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RecordatorioRepositoryIntegrationTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RecordatorioRepository recordatorioRepository;

    @Test
    public void shouldFindAllByUserID() {

        List<Recordatorio> found = recordatorioRepository.findByParticipanteID(Long.valueOf("10002"));

        assertEquals(1, found.size());
    }

    @Test
    public void shouldFindLatestReminderByUserID() {

        Recordatorio found = recordatorioRepository.getMostRecent(Long.valueOf("10002"));

        assertEquals("Etiqueta", found.getEtiqueta());
    }
    
}
