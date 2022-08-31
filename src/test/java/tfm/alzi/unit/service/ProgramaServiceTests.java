package tfm.alzi.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import tfm.alzi.models.Programa;
import tfm.alzi.repositories.ParticipanteProgramaRepository;
import tfm.alzi.repositories.ProgramaRepository;
import tfm.alzi.repositories.UsuarioRepository;
import tfm.alzi.services.ProgramaService;

@RunWith(SpringRunner.class)
@DataJpaTest
//@Sql(scripts = "/data.sql")
@ActiveProfiles("test")
public class ProgramaServiceTests {
    
    @InjectMocks
    protected ProgramaService programaService;

    @Mock
    private ProgramaRepository programaRepository;

    @MockBean
    private ParticipanteProgramaRepository participanteProgramaRepository;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

    @Test
    public void shouldFindProgramaWithCorrectId(){
        Programa p = new Programa();
        //p.setId(1);
        p.setUsuarioId((long) 10002);
        p.setTitulo("Memoria I");
        p.setDescripcion("Esto es una descripción.");
        p.setArea("Atención");
        p.setDuracion(3);
        p.setPuntuacion(4);
        
        when(programaRepository.getById((long) 1)).thenReturn(p);

        Programa found = this.programaService.getProgramaById(1);

        assertEquals(p.getTitulo(), found.getTitulo());
    }

}
