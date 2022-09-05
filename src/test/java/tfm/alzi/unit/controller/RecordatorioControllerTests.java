package tfm.alzi.unit.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import java.util.ArrayList;
import java.util.List;

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
import tfm.alzi.controllers.RecordatorioController;
import tfm.alzi.models.Recordatorio;
import tfm.alzi.models.Usuario;
import tfm.alzi.repositories.RecordatorioRepository;
import tfm.alzi.repositories.UsuarioCuidadorRepository;
import tfm.alzi.repositories.UsuarioEspecialistaRepository;
import tfm.alzi.repositories.UsuarioRepository;
import tfm.alzi.services.RecordatorioService;
import tfm.alzi.services.UsuarioService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = RecordatorioController.class, excludeAutoConfiguration = SecurityConfiguration.class)
@AutoConfigureMockMvc
public class RecordatorioControllerTests {

    @Autowired
	private WebApplicationContext context;

    @Autowired
    private MockMvc mvc;

    @MockBean(name = "usuarioService")
    private UsuarioService usuarioService;

    @MockBean
    private RecordatorioService recordatorioService;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @MockBean
    private RecordatorioRepository recordatorioRepository;

    @MockBean
    private UsuarioCuidadorRepository usuarioCuidadorRepository;

    @MockBean
    private UsuarioEspecialistaRepository usuarioEspecialistaRepository;

    @WithMockUser(username = "Y1234569W", authorities = {"ESPECIALISTA"})
    @Test
    public void shouldShowCreateReminderForm() throws Exception {

        Usuario usuario = new Usuario();
        usuario.setNombre("Isabel");
        usuario.setApellidos("Fire");
        usuario.setDni("Y1234569W");

        given(this.usuarioService.getUsuarioByDNI(usuario.getDni()))
            .willReturn(usuario);

        List<Recordatorio> ls = new ArrayList<>();

        given(this.recordatorioService.getRecordatoriosByID(Long.valueOf("10002")))
            .willReturn(ls);

        mvc.perform(get("/crear-recordatorio"))
            .andExpect(status().isOk())
            .andExpect(view().name("formNewRecordatorio"));
    }

    @WithMockUser(username = "Y1234569W", authorities = {"ESPECIALISTA"})
    @Test
    public void shouldCreateReminder() throws Exception {

        Usuario usuario = new Usuario();
        usuario.setNombre("Isabel");
        usuario.setApellidos("Fire");
        usuario.setDni("Y1234569W");

        given(this.usuarioService.getUsuarioByDNI(usuario.getDni()))
            .willReturn(usuario);

        List<Recordatorio> ls = new ArrayList<>();

        given(this.recordatorioService.getRecordatoriosByID(Long.valueOf("10002")))
            .willReturn(ls);

        mvc.perform(post("/crear-recordatorio")
            .param("usuarioId","10002")
            .param("fecha", "2022-10-10T15:30")
            .param("etiqueta", "Esto es una etiqueta.")
            .param("descripcion", "Esto es una descripción."))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/area-personal"));
    }

    @WithMockUser(username = "Y1234569W", authorities = {"ESPECIALISTA"})
    @Test
    public void shouldNotCreateReminder() throws Exception {

        Usuario usuario = new Usuario();
        usuario.setNombre("Isabel");
        usuario.setApellidos("Fire");
        usuario.setDni("Y1234569W");

        given(this.usuarioService.getUsuarioByDNI(usuario.getDni()))
            .willReturn(usuario);

        List<Recordatorio> ls = new ArrayList<>();

        given(this.recordatorioService.getRecordatoriosByID(Long.valueOf("10002")))
            .willReturn(ls);

        mvc.perform(post("/crear-recordatorio")
            .param("usuarioId","10002")
            .param("fecha", "2022-10-10T15:30")
            .param("etiqueta", "")
            .param("descripcion", "Esto es una descripción."))
        .andExpect(status().isOk())
        .andExpect(view().name("formNewRecordatorio"));
    }

    @WithMockUser(username = "Y1234569W", authorities = {"ESPECIALISTA"})
    @Test
    public void shouldShowEditReminderForm() throws Exception {

        Usuario usuario = new Usuario();
        usuario.setNombre("Isabel");
        usuario.setApellidos("Fire");
        usuario.setDni("Y1234569W");

        given(this.usuarioService.getUsuarioByDNI(usuario.getDni()))
            .willReturn(usuario);

        List<Recordatorio> ls = new ArrayList<>();

        given(this.recordatorioService.getRecordatoriosByID(Long.valueOf("10002")))
            .willReturn(ls);

        Recordatorio r = new Recordatorio();

        given(this.recordatorioService.getRecordatorioById(Long.valueOf("10001")))
            .willReturn(r);

        mvc.perform(get("/editar-recordatorio?id=10001"))
            .andExpect(status().isOk())
            .andExpect(view().name("formEditRecordatorio"));
    }

    @WithMockUser(username = "Y1234569W", authorities = {"ESPECIALISTA"})
    @Test
    public void shouldEditReminder() throws Exception {

        Usuario usuario = new Usuario();
        usuario.setNombre("Isabel");
        usuario.setApellidos("Fire");
        usuario.setDni("Y1234569W");

        given(this.usuarioService.getUsuarioByDNI(usuario.getDni()))
            .willReturn(usuario);

        List<Recordatorio> ls = new ArrayList<>();

        given(this.recordatorioService.getRecordatoriosByID(Long.valueOf("10002")))
            .willReturn(ls);

        mvc.perform(post("/crear-recordatorio")
            .param("usuarioId","10002")
            .param("id","10001")
            .param("fecha", "2022-10-10T15:30")
            .param("etiqueta", "Esto es una etiqueta.")
            .param("descripcion", "Esto es una descripción."))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/area-personal"));
    }

    @WithMockUser(username = "Y1234569W", authorities = {"ESPECIALISTA"})
    @Test
    public void shouldNotEditReminder() throws Exception {

        Usuario usuario = new Usuario();
        usuario.setNombre("Isabel");
        usuario.setApellidos("Fire");
        usuario.setDni("Y1234569W");

        given(this.usuarioService.getUsuarioByDNI(usuario.getDni()))
            .willReturn(usuario);

        List<Recordatorio> ls = new ArrayList<>();

        given(this.recordatorioService.getRecordatoriosByID(Long.valueOf("10002")))
            .willReturn(ls);

        mvc.perform(post("/editar-recordatorio")
            .param("usuarioId","10002")
            .param("id","10001")
            .param("fecha", "2022-10-10T15:30")
            .param("etiqueta", "")
            .param("descripcion", "Esto es una descripción."))
        .andExpect(status().isOk())
        .andExpect(view().name("formEditRecordatorio"));
    }

    @WithMockUser(username = "Y1234569W", authorities = {"ESPECIALISTA"})
    @Test
    public void shouldDeleteReminder() throws Exception {

        mvc.perform(get("/borrar-recordatorio/10001"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/area-personal"));
    }
    
}
