package tfm.alzi.e2e.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import jakarta.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EjercicioControllerE2ETests {
	
	@Autowired
	private WebApplicationContext context;

    @Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private tfm.alzi.controllers.EjercicioController EjercicioController;

    @WithMockUser(username = "Y1234569W", authorities = {"ESPECIALISTA"})
	@Test
	public void shouldListEjercicios() throws Exception {
		mockMvc.perform(get("/ejercicios"))
				.andExpect(status().isOk())
				.andExpect(view().name("ejercicios"));
	}

    @WithMockUser(username = "Y1234568W", authorities = {"USUARIO"})
	@Test
    @Transactional
	public void shouldShowDoEjercicioForm() throws Exception {
		mockMvc.perform(get("/do-ejercicio?programaId=10002&ejercicioId=10002"))
				.andExpect(status().isOk())
                .andExpect(model().attributeExists("ejercicio"))
				.andExpect(view().name("usuario/doEjercicio"));
	}
    
    @WithMockUser(username = "Y1234569W", authorities = {"ESPECIALISTA"})
	@Test
	public void shouldShowCreateEjercicioForm() throws Exception {
		mockMvc.perform(get("/crear-ejercicio"))
				.andExpect(status().isOk())
				.andExpect(view().name("especialista/formNewEjercicio"));
	}

    @WithMockUser(username = "Y1234569W", authorities = {"ESPECIALISTA"})
	@Test
    @Transactional
	public void shouldCrearEjercicio() throws Exception {
		mockMvc.perform(post("/crear-ejercicio")
                .param("usuarioId","10003")
                .param("titulo", "Ejercicio de prueba")
                .param("descripcion", "Esto es una descripción de prueba.")
                .param("duracion", "5")
                .param("preguntasArray", "10001"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("ejercicioCreado"))
			.andExpect(view().name("ejercicios"));
	}

    @WithMockUser(username = "Y1234569W", authorities = {"ESPECIALISTA"})
	@Test
	public void shouldShowEjercicio() throws Exception {
		mockMvc.perform(get("/ejercicio?id=10002"))
				.andExpect(status().isOk())
				.andExpect(view().name("especialista/showEjercicio"));
	}

    @WithMockUser(username = "Y1234569W", authorities = {"ESPECIALISTA"})
	@Test
    @Transactional
	public void shouldHideEjercicio() throws Exception {
		mockMvc.perform(get("/ocultar-ejercicio?id=10002"))
				.andExpect(status().isOk())
                .andExpect(model().attributeExists("ejercicioOcultado"))
				.andExpect(view().name("ejercicios"));
	}

    @WithMockUser(username = "Y1234569W", authorities = {"ESPECIALISTA"})
	@Test
    @Transactional
	public void shouldPublishEjercicio() throws Exception {
		mockMvc.perform(get("/publicar-ejercicio?id=10003"))
				.andExpect(status().isOk())
                .andExpect(model().attributeExists("ejercicioPublicado"))
				.andExpect(view().name("ejercicios"));
	}

    @WithMockUser(username = "Y1234569W", authorities = {"ESPECIALISTA"})
	@Test
    @Transactional
	public void shouldShowEjercicioUpdateForm() throws Exception {
		mockMvc.perform(get("/editar-ejercicio?id=10002"))
				.andExpect(status().isOk())
                .andExpect(model().attributeExists("ejercicio"))
				.andExpect(view().name("especialista/formEditEjercicio"));
	}

    @WithMockUser(username = "Y1234569W", authorities = {"ESPECIALISTA"})
	@Test
    @Transactional
	public void shouldShowEjercicioQuestionsUpdateForm() throws Exception {
		mockMvc.perform(get("/actualizar-preguntas?ejercicioId=10002"))
				.andExpect(status().isOk())
                .andExpect(model().attributeExists("preguntas"))
				.andExpect(view().name("especialista/actualizarPreguntas"));
	}

    @WithMockUser(username = "Y1234569W", authorities = {"ESPECIALISTA"})
	@Test
    @Transactional
	public void shouldDeleteEjercicio() throws Exception {
		mockMvc.perform(get("/borrar-ejercicio?id=10003"))
				.andExpect(status().isOk())
                .andExpect(model().attributeExists("ejercicioEliminado"))
				.andExpect(view().name("ejercicios"));
	}

}
