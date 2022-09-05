package tfm.alzi.unit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import jakarta.transaction.Transactional;
import tfm.alzi.models.ProgramaEjercicio;
import tfm.alzi.repositories.ProgramaEjercicioRepository;
import tfm.alzi.services.ProgramaEjercicioService;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class ProgramaEjercicioServiceTests {
    
    @InjectMocks
    protected ProgramaEjercicioService programaEjercicioService;

    @Mock
    private ProgramaEjercicioRepository programaEjercicioRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

    @Test
    public void shouldFindByProgramId(){
        ProgramaEjercicio pe1 = new ProgramaEjercicio();
        ProgramaEjercicio pe2 = new ProgramaEjercicio();
        ProgramaEjercicio pe3 = new ProgramaEjercicio();

        List<ProgramaEjercicio> ls = new ArrayList<ProgramaEjercicio>();
        ls.add(pe1);
        ls.add(pe2);
        ls.add(pe3);
        
        when(programaEjercicioRepository.findByProgramaID((long) 10001)).thenReturn(ls);

        List<ProgramaEjercicio> found = this.programaEjercicioService.getEjerciciosByProgramaID(Long.valueOf("10001"));

        assertEquals(ls.size(), found.size());
    }

    @Test
    public void shouldFindByExerciseId(){
        ProgramaEjercicio pe1 = new ProgramaEjercicio();
        ProgramaEjercicio pe2 = new ProgramaEjercicio();
        ProgramaEjercicio pe3 = new ProgramaEjercicio();

        List<ProgramaEjercicio> ls = new ArrayList<ProgramaEjercicio>();
        ls.add(pe1);
        ls.add(pe2);
        ls.add(pe3);
        
        when(programaEjercicioRepository.findByEjercicioId((long) 10001)).thenReturn(ls);

        List<ProgramaEjercicio> found = this.programaEjercicioService.findByEjercicioId(Long.valueOf("10001"));

        assertEquals(ls.size(), found.size());
    }

    @Test
    public void shouldFindByProgramIdEjercicioId(){
        ProgramaEjercicio pe1 = new ProgramaEjercicio();
        pe1.setProgramaId(Long.valueOf("10001"));
        
        when(programaEjercicioRepository.findByProgramaIdEjercicioId((long) 10001,(long) 10001)).thenReturn(pe1);

        ProgramaEjercicio found = this.programaEjercicioService.findByProgramaIdEjercicioId(Long.valueOf("10001"),Long.valueOf("10001"));

        assertEquals(pe1.getProgramaId(), found.getProgramaId());
    }

    @Test
    @Transactional
    public void shouldInsertRelacion(){
        ProgramaEjercicio pe1 = new ProgramaEjercicio();
        pe1.setProgramaId(Long.valueOf("10001"));
        pe1.setEjercicioId(Long.valueOf("10001"));

        programaEjercicioService.crearRelacion(pe1);

        List<ProgramaEjercicio> ls = new ArrayList<>();
        ls.add(pe1);

        when(programaEjercicioRepository.findByProgramaID((long) 10001)).thenReturn(ls);

        List<ProgramaEjercicio> found = this.programaEjercicioService.getEjerciciosByProgramaID(Long.valueOf("10001"));

        assertEquals(1, found.size());
        assertThat(pe1.getId()).isNotNull();
    }

    @Test
    @Transactional
    public void shouldDeleteProgramaEjercicioList(){
        ProgramaEjercicio pe1 = new ProgramaEjercicio();
        pe1.setProgramaId(Long.valueOf("10001"));
        pe1.setEjercicioId(Long.valueOf("10001"));

        List<ProgramaEjercicio> ls = new ArrayList<>();
        ls.add(pe1);

        programaEjercicioService.eliminarLista(ls);

        List<ProgramaEjercicio> empty = new ArrayList<>();

        when(programaEjercicioRepository.findByProgramaID((long) 10001)).thenReturn(empty);

        List<ProgramaEjercicio> found = this.programaEjercicioService.getEjerciciosByProgramaID(Long.valueOf("10001"));

        assertEquals(0, found.size());
    }

    @Test
    @Transactional
    public void shouldDeleteProgramaEjercicio(){
        ProgramaEjercicio pe1 = new ProgramaEjercicio();
        pe1.setProgramaId(Long.valueOf("10001"));
        pe1.setEjercicioId(Long.valueOf("10001"));

        programaEjercicioService.eliminarRelacion(pe1);

        ProgramaEjercicio empty = null;

        when(programaEjercicioRepository.findByProgramaIdEjercicioId((long) 10001,(long) 10001)).thenReturn(empty);

        ProgramaEjercicio found = this.programaEjercicioService.findByProgramaIdEjercicioId(Long.valueOf("10001"),Long.valueOf("10001"));

        assertEquals(null, found);
    }

}
