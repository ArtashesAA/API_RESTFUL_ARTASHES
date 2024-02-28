package com.example.concesionario.testControladores;

import com.example.concesionario.controlador.AuthController;
import com.example.concesionario.dto.ReqRes;
import com.example.concesionario.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;

@WebMvcTest(AuthController.class)
public class AutenticacionControladorTest {

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private AuthService authService;

	@InjectMocks
	private AuthController authController;

	@Test
	public void testSignUp() throws Exception {
		ReqRes signUpRequest = new ReqRes("usuarioPrueba", "contrasenaPrueba");
		ReqRes mockResponse = new ReqRes("Usuario registrado correctamente", "tokenDePrueba");

		when(authService.signUp(signUpRequest)).thenReturn(mockResponse);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/signup").content(asJsonString(signUpRequest))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(asJsonString(mockResponse)));
	}

	@Test
	public void testSignIn() throws Exception {
		ReqRes signInRequest = new ReqRes("usuarioPrueba", "contrasenaPrueba");
		ReqRes mockResponse = new ReqRes("Inicio de sesión exitoso", "tokenDePrueba");

		when(authService.signIn(signInRequest)).thenReturn(mockResponse);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/signin").content(asJsonString(signInRequest))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.token").value(mockResponse.getToken()));
	}

	// Método para convertir objeto a formato JSON
	private static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
