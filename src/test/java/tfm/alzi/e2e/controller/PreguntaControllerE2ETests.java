package tfm.alzi.e2e.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import jakarta.transaction.Transactional;
import tfm.alzi.controllers.PreguntaController;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PreguntaControllerE2ETests {
    
    @Autowired
	private WebApplicationContext context;

    @Autowired
	private MockMvc mockMvc;

    @Autowired
    private PreguntaController preguntaController;

    @WithMockUser(username = "Y1234569W", authorities = {"ESPECIALISTA"})
	@Test
	public void shouldListPreguntas() throws Exception {
		mockMvc.perform(get("/preguntas"))
				.andExpect(status().isOk())
				.andExpect(view().name("preguntas"));
	}

    @WithMockUser(username = "Y1234569W", authorities = {"ESPECIALISTA"})
	@Test
	public void shouldShowPregunta() throws Exception {
		mockMvc.perform(get("/pregunta?id=10001"))
				.andExpect(status().isOk())
                .andExpect(model().attributeExists("pregunta"))
				.andExpect(view().name("especialista/showPregunta"));
	}

    @WithMockUser(username = "Y1234569W", authorities = {"ESPECIALISTA"})
	@Test
	public void shouldShowCreatePreguntaFormPrev() throws Exception {
		mockMvc.perform(get("/crear-pregunta"))
				.andExpect(status().isOk())
                .andExpect(view().name("especialista/formNewPreguntaPrev"));
	}

    @WithMockUser(username = "Y1234569W", authorities = {"ESPECIALISTA"})
	@Test
	public void shouldRedirectToCreateCompletePreguntaForm() throws Exception {
		mockMvc.perform(post("/crear-pregunta")
                .param("tipo", "COMPLETA"))
				.andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/crear-pregunta-completa"));
	}

    @WithMockUser(username = "Y1234569W", authorities = {"ESPECIALISTA"})
	@Test
	public void shouldRedirectToCreateUniquePreguntaForm() throws Exception {
		mockMvc.perform(post("/crear-pregunta")
                .param("tipo", "UNIQUE"))
				.andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/crear-pregunta-unique"));
	}

    @WithMockUser(username = "Y1234569W", authorities = {"ESPECIALISTA"})
	@Test
	public void shouldRedirectToCreateMultiPreguntaForm() throws Exception {
		mockMvc.perform(post("/crear-pregunta")
                .param("tipo", "MULTI"))
				.andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/crear-pregunta-multi"));
	}

    @WithMockUser(username = "Y1234569W", authorities = {"ESPECIALISTA"})
	@Test
    @Transactional
	public void shouldCreateCompletePregunta() throws Exception {
		mockMvc.perform(post("/crear-pregunta-completa")
                .param("id", "1")
                .param("usuarioId", "10003")
                .param("tipo", "COMPLETA")
                .param("publico", "false")
                .param("descripcion", "Descripción de prueba.")
                .param("question", "Pregunta de prueba.")
                .param("answer", "Respuesta de prueba."))
				.andExpect(status().isOk())
                .andExpect(view().name("preguntas"));
	}

    @WithMockUser(username = "Y1234569W", authorities = {"ESPECIALISTA"})
	@Test
    @Transactional
	public void shouldNotCreateCompletePregunta() throws Exception {
		mockMvc.perform(post("/crear-pregunta-completa")
                .param("id", "1")
                .param("usuarioId", "10003")
                .param("tipo", "COMPLETA")
                .param("publico", "false")
                .param("descripcion", "")
                .param("question", "Pregunta de prueba.")
                .param("answer", "Respuesta de prueba."))
				.andExpect(status().isOk())
                .andExpect(view().name("especialista/formNewPregunta"));
	}

    @WithMockUser(username = "Y1234569W", authorities = {"ESPECIALISTA"})
	@Test
    @Transactional
	public void shouldHidePregunta() throws Exception {
		mockMvc.perform(get("/ocultar-pregunta?id=10001"))
				.andExpect(status().isOk())
                .andExpect(model().attributeExists("preguntaOcultada"))
				.andExpect(view().name("preguntas"));
	}

    @WithMockUser(username = "Y1234569W", authorities = {"ESPECIALISTA"})
	@Test
    @Transactional
	public void shouldPublishEjercicio() throws Exception {
		mockMvc.perform(get("/publicar-pregunta?id=10008"))
				.andExpect(status().isOk())
                .andExpect(model().attributeExists("preguntaPublicada"))
				.andExpect(view().name("preguntas"));
	}

    @WithMockUser(username = "Y1234569W", authorities = {"ESPECIALISTA"})
	@Test
    @Transactional
	public void shouldDeleteEjercicio() throws Exception {
		mockMvc.perform(get("/borrar-pregunta?id=10008"))
				.andExpect(status().isOk())
                .andExpect(model().attributeExists("preguntaEliminada"))
				.andExpect(view().name("preguntas"));
	}

}
