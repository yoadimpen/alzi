package tfm.alzi.unit.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import tfm.alzi.configuration.SecurityConfiguration;
import tfm.alzi.controllers.GenericController;
import tfm.alzi.models.Ejercicio;
import tfm.alzi.models.Programa;
import tfm.alzi.models.Recordatorio;
import tfm.alzi.models.Usuario;
import tfm.alzi.models.UsuarioCuidador;
import tfm.alzi.repositories.EjercicioRepository;
import tfm.alzi.repositories.ParticipanteProgramaRepository;
import tfm.alzi.repositories.ProgramaRepository;
import tfm.alzi.repositories.RecordatorioRepository;
import tfm.alzi.repositories.UsuarioCuidadorRepository;
import tfm.alzi.repositories.UsuarioEspecialistaRepository;
import tfm.alzi.repositories.UsuarioRepository;
import tfm.alzi.services.EjercicioService;
import tfm.alzi.services.ParticipanteProgramaService;
import tfm.alzi.services.ProgramaService;
import tfm.alzi.services.RecordatorioService;
import tfm.alzi.services.UsuarioCuidadorService;
import tfm.alzi.services.UsuarioService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = GenericController.class, excludeAutoConfiguration = SecurityConfiguration.class)
@AutoConfigureMockMvc
public class GenericControllerTests {
    
    @Autowired
	private WebApplicationContext context;

    @Autowired
    private MockMvc mvc;

    @MockBean(name = "usuarioService")
    private UsuarioService usuarioService;

    @MockBean
    private RecordatorioService recordatorioService;

    @MockBean
    private ProgramaService programaService;

    @MockBean
    private EjercicioService ejercicioService;

    @MockBean
    private UsuarioCuidadorService usuarioCuidadorService;

    @MockBean
    private ParticipanteProgramaService participanteProgramaService;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @MockBean
    private RecordatorioRepository recordatorioRepository;

    @MockBean
    private ProgramaRepository programaRepository;

    @MockBean
    private EjercicioRepository ejercicioRepository;

    @MockBean
    private UsuarioCuidadorRepository usuarioCuidadorRepository;

    @MockBean
    private ParticipanteProgramaRepository participanteProgramaRepository;

    @MockBean
    private UsuarioEspecialistaRepository usuarioEspecialistaRepository;

    @WithMockUser(username = "Y1234569W", authorities = {"ESPECIALISTA"})
    @Test
    public void shouldShowHomePage() throws Exception {

        mvc.perform(get("/alzi"))
            .andExpect(status().isOk())
            .andExpect(view().name("index"));
    }

    @Test
    public void shouldShowLoginPage() throws Exception {

        mvc.perform(get("/alzi"))
            .andExpect(status().isOk())
            .andExpect(view().name("login"));
    }

    @WithMockUser(username = "Y1234569W", authorities = {"ESPECIALISTA"})
    @Test
    public void shouldShowPersonalAreaPage() throws Exception {

        Usuario usuario = new Usuario();
        usuario.setNombre("Isabel");
        usuario.setApellidos("Fire");
        usuario.setDni("Y1234569W");

        given(this.usuarioService.getUsuarioByDNI(usuario.getDni()))
            .willReturn(usuario);

        List<Recordatorio> ls = new ArrayList<>();

        given(this.recordatorioService.getRecordatoriosByID(Long.valueOf("10002")))
            .willReturn(ls);

        mvc.perform(get("/area-personal"))
            .andExpect(status().isOk())
            .andExpect(view().name("areaPersonal"));
    }

    @WithMockUser(username = "Y1234568W", authorities = {"USUARIO"})
    @Test
    public void shouldShowPlanPage() throws Exception {

        Usuario usuario = new Usuario();
        usuario.setNombre("Mary");
        usuario.setApellidos("Jane");
        usuario.setDni("Y1234568W");
        usuario.setRoles("USUARIO");

        given(this.usuarioService.getUsuarioByDNI(usuario.getDni()))
            .willReturn(usuario);

        Programa p = new Programa();

        given(this.programaService.getProgramaById(Long.valueOf("10001")))
            .willReturn(p);

        mvc.perform(get("/entrenamiento"))
            .andExpect(status().isOk())
            .andExpect(view().name("usuario/showPlan"));
    }

    @WithMockUser(username = "Y1234569W", authorities = {"ESPECIALISTA"})
    @Test
    public void shouldShowProgramsPage() throws Exception {

        Usuario usuario = new Usuario();
        usuario.setNombre("Isabel");
        usuario.setApellidos("Fire");
        usuario.setDni("Y1234569W");

        given(this.usuarioService.getUsuarioByDNI(usuario.getDni()))
            .willReturn(usuario);

        List<Programa> programas = new ArrayList<>();

        List<Programa> miProgramas = new ArrayList<>();

        given(this.programaService.getAllPublicProgramas())
            .willReturn(programas);

        given(this.programaService.getMyPrivateProgramas(Long.valueOf("10002")))
            .willReturn(miProgramas);

        mvc.perform(get("/programas"))
            .andExpect(status().isOk())
            .andExpect(view().name("programas"));
    }

    @WithMockUser(username = "Y1234569W", authorities = {"ESPECIALISTA"})
    @Test
    public void shouldShowExercisesPage() throws Exception {

        Usuario usuario = new Usuario();
        usuario.setNombre("Isabel");
        usuario.setApellidos("Fire");
        usuario.setDni("Y1234569W");

        given(this.usuarioService.getUsuarioByDNI(usuario.getDni()))
            .willReturn(usuario);

        List<Ejercicio> ejercicios = new ArrayList<>();

        List<Ejercicio> miEjercicios = new ArrayList<>();

        given(this.ejercicioService.getAllPublicEjercicios())
            .willReturn(ejercicios);

        given(this.ejercicioService.getMyPrivateEjercicios(Long.valueOf("10002")))
            .willReturn(miEjercicios);

        mvc.perform(get("/ejercicios"))
            .andExpect(status().isOk())
            .andExpect(view().name("ejercicios"));
    }

    @WithMockUser(username = "Y1234569W", authorities = {"ESPECIALISTA"})
    @Test
    public void shouldShowProfilePage() throws Exception {

        Usuario usuario = new Usuario();
        usuario.setNombre("Isabel");
        usuario.setApellidos("Fire");
        usuario.setDni("Y1234569W");

        given(this.usuarioService.getUsuarioByDNI(usuario.getDni()))
            .willReturn(usuario);

        given(this.usuarioService.getUsuarioById(Long.valueOf("10002")))
            .willReturn(usuario);
            
        UsuarioCuidador uc = new UsuarioCuidador();

        given(this.usuarioCuidadorService.findByUsuarioId(Long.valueOf("10002")))
            .willReturn(uc);

        mvc.perform(get("/perfil"))
            .andExpect(status().isOk())
            .andExpect(view().name("perfil"));
    }

}
