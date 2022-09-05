package tfm.alzi.unit.service;

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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import tfm.alzi.models.ParticipantePrograma;
import tfm.alzi.models.Programa;
import tfm.alzi.models.Usuario;
import tfm.alzi.repositories.ParticipanteProgramaRepository;
import tfm.alzi.repositories.ProgramaRepository;
import tfm.alzi.repositories.UsuarioRepository;
import tfm.alzi.services.ProgramaService;

@RunWith(SpringRunner.class)
@DataJpaTest
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

    @Test
    public void shouldNotFindProgramaWithIncorrectId(){

        Programa found = this.programaService.getProgramaById(-1);

        assertEquals(null, found);
    }

    @WithMockUser(username = "Y1234568W", authorities = {"USUARIO"})
    @Test
    public void shouldFindMyPrograms(){

        Usuario usuario = new Usuario();
        usuario.setId((long) 10001);
        usuario.setNombre("Mary");
        usuario.setApellidos("Jane");
        usuario.setDni("Y1234568W");

        ParticipantePrograma pp1 = new ParticipantePrograma();
        pp1.setUsuarioId((long) 10001);
        pp1.setProgramaId((long) 10001);
        ParticipantePrograma pp2 = new ParticipantePrograma();
        pp2.setUsuarioId((long) 10001);
        pp2.setProgramaId((long) 10002);

        List<ParticipantePrograma> ls = new ArrayList<>();
        ls.add(pp1);
        ls.add(pp2);

        Programa p1 = new Programa();
        p1.setPublico(true);
        Programa p2 = new Programa();
        p2.setPublico(true);

        when(this.usuarioRepository.findByDNI(usuario.getDni())).thenReturn(usuario);

        when(participanteProgramaRepository.findByUsuarioID((long) 10001)).thenReturn(ls);
        
        when(programaRepository.getById((long) 10001)).thenReturn(p1);
        when(programaRepository.getById((long) 10002)).thenReturn(p2);

        assertEquals(ls.size(), this.programaService.getMyProgramas().size());
    }

    @Test
    public void shouldFindAllPrograms(){

        Programa p1 = new Programa();
        List<Programa> ls = new ArrayList<Programa>();
        ls.add(p1);

        when(programaRepository.findAll()).thenReturn(ls);

        List<Programa> found = this.programaService.getAllProgramas();

        assertEquals(ls.size(), found.size());
    }

    @Test
    public void shouldFindAllPublicPrograms(){

        Programa p1 = new Programa();
        List<Programa> ls = new ArrayList<Programa>();
        ls.add(p1);

        when(programaRepository.findAllPublic()).thenReturn(ls);

        List<Programa> found = this.programaService.getAllPublicProgramas();

        assertEquals(ls.size(), found.size());
    }

    @WithMockUser(username = "Y1234569W", authorities = {"ESPECIALISTA"})
    @Test
    public void shouldFindPrivatePrograms(){

        Programa p1 = new Programa();
        List<Programa> ls = new ArrayList<Programa>();
        ls.add(p1);

        when(programaRepository.findPrivateByUserId(Long.valueOf("10002"))).thenReturn(ls);

        List<Programa> found = this.programaService.getMyPrivateProgramas(Long.valueOf("10002"));

        assertEquals(ls.size(), found.size());
    }

}
