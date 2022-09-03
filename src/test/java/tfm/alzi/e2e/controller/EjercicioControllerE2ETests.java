package tfm.alzi.e2e.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import jakarta.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EjercicioControllerE2ETests {

    private static final int EJERCICIO_ID = 1;
	
	@Autowired
	private WebApplicationContext context;

    @Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private tfm.alzi.controllers.EjercicioController EjercicioController;
	
	@BeforeEach
	void setup() {
        
	}

    @WithMockUser(username = "Y1234569W", authorities = {"ESPECIALISTA"})
	@Test
	public void shouldListEjercicios() throws Exception {
		mockMvc.perform(get("/ejercicios"))
				.andExpect(status().isOk())
				.andExpect(view().name("ejercicios"));
	}
    
}
