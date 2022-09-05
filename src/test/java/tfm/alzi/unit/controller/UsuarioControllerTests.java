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
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import tfm.alzi.configuration.SecurityConfiguration;
import tfm.alzi.controllers.UsuarioController;
import tfm.alzi.models.ParticipantePrograma;
import tfm.alzi.models.Usuario;
import tfm.alzi.repositories.ParticipanteProgramaRepository;
import tfm.alzi.repositories.ProgramaRepository;
import tfm.alzi.repositories.UsuarioCuidadorRepository;
import tfm.alzi.repositories.UsuarioEspecialistaRepository;
import tfm.alzi.repositories.UsuarioRepository;
import tfm.alzi.services.ParticipanteProgramaService;
import tfm.alzi.services.ProgramaService;
import tfm.alzi.services.UsuarioCuidadorService;
import tfm.alzi.services.UsuarioEspecialistaService;
import tfm.alzi.services.UsuarioService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UsuarioController.class, excludeAutoConfiguration = SecurityConfiguration.class)
@AutoConfigureMockMvc
public class UsuarioControllerTests {

    @Autowired
	private WebApplicationContext context;

    @Autowired
    private MockMvc mvc;

    @MockBean(name = "usuarioService")
    private UsuarioService usuarioService;

    @MockBean
    private ParticipanteProgramaService participanteProgramaService;

    @MockBean
    private ProgramaService programaService;

    @MockBean
    private UsuarioCuidadorService usuarioCuidadorService;

    @MockBean
    private UsuarioEspecialistaService usuarioEspecialistaService;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @MockBean
    private ParticipanteProgramaRepository participanteProgramaRepository;

    @MockBean
    private ProgramaRepository programaRepository;

    @MockBean
    private UsuarioCuidadorRepository usuarioCuidadorRepository;

    @MockBean
    private UsuarioEspecialistaRepository usuarioEspecialistaRepository;

    @BeforeEach
    public void setUp(WebApplicationContext context) {
        Usuario usuario = new Usuario();
        usuario.setNombre("Mary");
        usuario.setApellidos("Jane");
        usuario.setDni("Y1234568W");

        Mockito.when(this.usuarioService.getUsuarioByDNI(usuario.getDni()))
            .thenReturn(usuario);
    }

    @Test
    public void shouldShowLoginForm() throws Exception {
        mvc.perform(get("/login"))
            .andExpect(status().isOk())
            .andExpect(view().name("login"));
    }

    @WithMockUser(username = "Y1234568W", authorities = {"USUARIO"})
    @Test
    public void shouldRedirectFromLoginWhenAuthenticated() throws Exception {
        mvc.perform(get("/login"))
            .andExpect(status().isOk())
            .andExpect(view().name("index"));
    }

    @Test
    public void shouldShowCreateUserForm() throws Exception {
        mvc.perform(get("/crear-usuario"))
            .andExpect(status().isOk())
            .andExpect(view().name("formNewUsuario"));
    }

    @Test
    public void shouldCreateNewUser() throws Exception {
        mvc.perform(post("/crear-usuario")
                .param("nombre","Yoana")
                .param("apellidos", "Guiri")
                .param("fechaNacimiento", "1999-04-04")
                .param("dni", "Y0282957W")
                .param("pass", "P@ssw0rd")
                .param("direccion", "Plaza")
                .param("telefono", "681359999")
                .param("email", "yoanaguiri@gmail.com")
                .param("bio", "")
                .param("roles", "CUIDADOR")
                .param("CTipo","INFORMAL"))
            .andExpect(status().isOk())
            .andExpect(view().name("login"));
    }

    @WithMockUser(username = "Y1234568W", authorities = {"USUARIO"})
    @Test
    public void shouldShowEditUserForm() throws Exception {

        Usuario usuario = new Usuario();
        usuario.setNombre("Mary");
        usuario.setApellidos("Jane");
        usuario.setDni("Y1234568W");

        given(this.usuarioService.getUsuarioByDNI(usuario.getDni()))
            .willReturn(usuario);

        mvc.perform(get("/editar-usuario"))
            .andExpect(status().isOk())
            .andExpect(view().name("formEditUsuario"));
    }

    @WithMockUser(username = "Y1234568W", authorities = {"USUARIO"})
    @Test
    public void shouldEditUser() throws Exception {

        mvc.perform(post("/editar-usuario")
                .param("nombre","Mary")
                .param("apellidos", "Jane")
                .param("fechaNacimiento", "1969-04-04")
                .param("dni", "Y1234568W")
                .param("pass", "Pass1234")
                .param("passCheck", "Pass1234")
                .param("direccion", "Calle Riverdale 4")
                .param("telefono", "600000002")
                .param("email", "maryjane.usuario@gmail.com")
                .param("bio", "Esta es mi nueva bio.")
                .param("roles", "USUARIO")
                .param("PRelacionCuidador","Hija"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("successEdit"))
            .andExpect(view().name("perfil"));
    }

    @WithMockUser(username = "Y1234568W", authorities = {"USUARIO"})
    @Test
    public void shouldDeleteUser() throws Exception {

        mvc.perform(get("/eliminar-usuario"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("usuarioEliminado"))
            .andExpect(view().name("login"));
    }

    @WithMockUser(username = "Y1234569W", authorities = {"ESPECIALISTA"})
    @Test
    public void shouldShowUserProfile() throws Exception {

        Usuario usuario = new Usuario();
        usuario.setNombre("Mary");
        usuario.setApellidos("Jane");
        usuario.setDni("Y1234568W");

        given(this.usuarioService.getUsuarioById(Long.valueOf("10002")))
            .willReturn(usuario);

        List<ParticipantePrograma> ls = new ArrayList<>();

        given(this.participanteProgramaService.getSuscripcionesByID(Long.valueOf("10002")))
            .willReturn(ls);

        mvc.perform(get("/show-perfil?usuarioId=10002"))
            .andExpect(status().isOk())
            .andExpect(view().name("showPerfil"));
    }

    @WithMockUser(username = "Y1234568W", authorities = {"USUARIO"})
    @Test
    public void shouldShowCarerForm() throws Exception {

        Usuario usuario = new Usuario();
        usuario.setNombre("Mary");
        usuario.setApellidos("Jane");
        usuario.setDni("Y1234568W");

        given(this.usuarioService.getUsuarioByDNI(usuario.getDni()))
            .willReturn(usuario);

        List<Usuario> ls = new ArrayList<>();

        given(this.usuarioService.getAllCuidadores())
            .willReturn(ls);

        mvc.perform(get("/carer"))
            .andExpect(status().isOk())
            .andExpect(view().name("usuario/carer"));
    }

    @WithMockUser(username = "Y1234568W", authorities = {"USUARIO"})
    @Test
    public void shouldAddCarer() throws Exception {

        Usuario usuario = new Usuario();
        usuario.setNombre("Mary");
        usuario.setApellidos("Jane");
        usuario.setDni("Y1234568W");

        given(this.usuarioService.getUsuarioById(Long.valueOf("10002")))
            .willReturn(usuario);

        mvc.perform(post("/carer?usuarioId=10002")
                .param("cuidadores","10007"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/perfil"));
    }

    @WithMockUser(username = "Y1234569W", authorities = {"ESPECIALISTA"})
    @Test
    public void shouldShowAddUserForm() throws Exception {

        Usuario usuario = new Usuario();
        usuario.setNombre("Isabel");
        usuario.setApellidos("Fire");
        usuario.setDni("Y1234569W");

        given(this.usuarioService.getUsuarioByDNI(usuario.getDni()))
            .willReturn(usuario);

        List<Usuario> ls = new ArrayList<>();

        given(this.usuarioService.getAllUsuarios())
            .willReturn(ls);

        mvc.perform(get("/usuario"))
            .andExpect(status().isOk())
            .andExpect(view().name("especialista/usuario"));
    }

    @WithMockUser(username = "Y1234569W", authorities = {"ESPECIALISTA"})
    @Test
    public void shouldAddNewUser() throws Exception {

        Usuario usuario = new Usuario();
        usuario.setNombre("Isabel");
        usuario.setApellidos("Fire");
        usuario.setDni("Y1234569W");

        List<Usuario> ls = new ArrayList<>();

        given(this.usuarioService.getAllUsuarios())
            .willReturn(ls);

        given(this.usuarioService.getUsuarioByDNI(usuario.getDni()))
            .willReturn(usuario);

        given(this.usuarioEspecialistaService.findByBoth(Long.valueOf("10003"),Long.valueOf("10004")))
            .willReturn(null);

        mvc.perform(post("/usuario?especialistaId=10003")
                .param("usuarios","10004"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/alzi"));
    }
    
}
