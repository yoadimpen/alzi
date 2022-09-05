package tfm.alzi.unit.service;

import static org.junit.Assert.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;
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
import tfm.alzi.models.InformePregunta;
import tfm.alzi.repositories.InformePreguntaRepository;
import tfm.alzi.services.InformePreguntaService;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class InformePreguntaServiceTests {

    @InjectMocks
    protected InformePreguntaService informePreguntaService;

    @Mock
    private InformePreguntaRepository informePreguntaRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

    @Test
    public void shouldFindInformePreguntaWithCorrectId(){
        InformePregunta ip = new InformePregunta();

        ip.setRespuesta("Esta es la respuesta.");
        
        when(informePreguntaRepository.getById((long) 10001)).thenReturn(ip);

        InformePregunta found = this.informePreguntaService.getInformePreguntaById(Long.valueOf("10001"));

        assertEquals(ip.getRespuesta(), found.getRespuesta());
    }

    @Test
    public void shouldFindCustomInformePregunta(){
        InformePregunta ip1 = new InformePregunta();
        InformePregunta ip2 = new InformePregunta();
        InformePregunta ip3 = new InformePregunta();

        List<InformePregunta> ls = new ArrayList<InformePregunta>();
        ls.add(ip1);
        ls.add(ip2);
        ls.add(ip3);
        
        when(informePreguntaRepository.findCustom((long) 10001,(long) 10001,(long) 10001)).thenReturn(ls);

        List<InformePregunta> found = this.informePreguntaService.getInformesPreguntaCustom(Long.valueOf("10001"),Long.valueOf("10001"),Long.valueOf("10001"));

        assertEquals(ls.size(), found.size());
    }

    @Test
    public void shouldFindByProgramId(){
        InformePregunta ip1 = new InformePregunta();
        InformePregunta ip2 = new InformePregunta();
        InformePregunta ip3 = new InformePregunta();

        List<InformePregunta> ls = new ArrayList<InformePregunta>();
        ls.add(ip1);
        ls.add(ip2);
        ls.add(ip3);
        
        when(informePreguntaRepository.findByProgramaId((long) 10001)).thenReturn(ls);

        List<InformePregunta> found = this.informePreguntaService.findByProgramaId(Long.valueOf("10001"));

        assertEquals(ls.size(), found.size());
    }

    @Test
    public void shouldFindByProgramIdEjercicioId(){
        InformePregunta ip1 = new InformePregunta();
        InformePregunta ip2 = new InformePregunta();
        InformePregunta ip3 = new InformePregunta();

        List<InformePregunta> ls = new ArrayList<InformePregunta>();
        ls.add(ip1);
        ls.add(ip2);
        ls.add(ip3);
        
        when(informePreguntaRepository.findByProgramaIdEjercicioId((long) 10001,(long) 10001)).thenReturn(ls);

        List<InformePregunta> found = this.informePreguntaService.findByProgramaIdEjercicioId(Long.valueOf("10001"),Long.valueOf("10001"));

        assertEquals(ls.size(), found.size());
    }

    @Test
    public void shouldFindByEjercicioId(){
        InformePregunta ip1 = new InformePregunta();
        InformePregunta ip2 = new InformePregunta();
        InformePregunta ip3 = new InformePregunta();

        List<InformePregunta> ls = new ArrayList<InformePregunta>();
        ls.add(ip1);
        ls.add(ip2);
        ls.add(ip3);
        
        when(informePreguntaRepository.findByEjercicioId((long) 10001)).thenReturn(ls);

        List<InformePregunta> found = this.informePreguntaService.findByEjercicioId(Long.valueOf("10001"));

        assertEquals(ls.size(), found.size());
    }

    @Test
    @Transactional
    public void shouldInsertInformePregunta(){
        InformePregunta ip = new InformePregunta();
        ip.setProgramaId((long) 10001);
        ip.setEjercicioId((long) 10001);
        ip.setPreguntaId((long) 10001);
        ip.setUsuarioId((long) 10001);
        ip.setRespuesta("Respuesta");
        ip.setResultado(true);

        informePreguntaService.crearInforme(ip);

        List<InformePregunta> ls = new ArrayList<>();
        ls.add(ip);

        when(informePreguntaRepository.findByProgramaId((long) 10001)).thenReturn(ls);

        List<InformePregunta> found = this.informePreguntaService.findByProgramaId(Long.valueOf("10001"));

        assertEquals(1, found.size());
        assertThat(ip.getId()).isNotNull();
    }

    @Test
    @Transactional
    public void shouldDeleteInformePregunta(){
        InformePregunta ip = new InformePregunta();
        ip.setProgramaId((long) 10001);
        ip.setEjercicioId((long) 10001);
        ip.setPreguntaId((long) 10001);
        ip.setUsuarioId((long) 10001);
        ip.setRespuesta("Respuesta");
        ip.setResultado(true);

        List<InformePregunta> ls = new ArrayList<>();
        ls.add(ip);

        informePreguntaService.eliminarLista(ls);

        List<InformePregunta> empty = new ArrayList<>();

        when(informePreguntaRepository.findByProgramaId((long) 10001)).thenReturn(empty);

        List<InformePregunta> found = this.informePreguntaService.findByProgramaId(Long.valueOf("10001"));

        assertEquals(0, found.size());
    }
    
}
