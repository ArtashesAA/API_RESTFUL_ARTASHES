package com.example.concesionario.testControladores;

import com.example.concesionario.ConcesionarioApplication;
import com.example.concesionario.entidad.Coche;
import com.example.concesionario.repositorio.CocheRepositorio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = ConcesionarioApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CocheControladorTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private CocheRepositorio cocheRepositorio;

	private String obtenerTokenDeAutenticacion() {
		// Token de usuario iniciado
		// **Caduca cada 24h
		return "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBleGFtcGxlLmNvbSIsImlhdCI6MTcwOTcyMjUzNCwiZXhwIjoxNzA5ODA4OTM0fQ.6DyDcQAhB-VYy6BJCm4bWALFVAmZ14hYwK2WkrNUsu8";
	}

	@Test
	public void pruebaObtenerTodosLosCoches() {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(obtenerTokenDeAutenticacion());

		HttpEntity<Void> request = new HttpEntity<>(headers);

		ResponseEntity<Coche[]> respuesta = restTemplate.exchange("http://localhost:" + port + "/api/v1/coche",
				HttpMethod.GET, request, Coche[].class);

		assertEquals(HttpStatus.OK, respuesta.getStatusCode());
		assertNotNull(respuesta.getBody());
	}

	@Test
	public void pruebaObtenerCochePorId() {
		Long idCocheExistente = 1L;
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(obtenerTokenDeAutenticacion());
		HttpEntity<Void> request = new HttpEntity<>(headers);

		ResponseEntity<Coche> respuesta = restTemplate.exchange(
				"http://localhost:" + port + "/api/v1/coche/" + idCocheExistente, HttpMethod.GET, request, Coche.class);

		assertEquals(HttpStatus.OK, respuesta.getStatusCode());
		assertNotNull(respuesta.getBody());
		assertEquals(idCocheExistente, respuesta.getBody().getId());
	}

	@Test
	public void pruebaCrearCoche() {
		Coche nuevoCoche = new Coche(1L, "NuevoCoche", "Modelo", 2022, 150, 50000, 1500, "Gasolina", "Blanco", 25000,
				"Descripcion");
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(obtenerTokenDeAutenticacion());
		HttpEntity<Coche> entidadCrear = new HttpEntity<>(nuevoCoche, headers);

		ResponseEntity<Coche> respuesta = restTemplate.exchange("http://localhost:" + port + "/api/v1/coche",
				HttpMethod.POST, entidadCrear, Coche.class);

		assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());
		assertNotNull(respuesta.getBody());
		assertEquals(nuevoCoche.getModelo(), respuesta.getBody().getModelo());

		// Elimina el coche creado durante la prueba
		cocheRepositorio.deleteById(respuesta.getBody().getId());
	}

	@Test
	public void pruebaActualizarCoche() {
		Long idCocheExistente = 1L;
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(obtenerTokenDeAutenticacion());
		HttpEntity<Void> requestExistente = new HttpEntity<>(headers);

		ResponseEntity<Coche> respuestaExistente = restTemplate.exchange(
				"http://localhost:" + port + "/api/v1/coche/" + idCocheExistente, HttpMethod.GET, requestExistente,
				Coche.class);

		assertEquals(HttpStatus.OK, respuestaExistente.getStatusCode());
		assertNotNull(respuestaExistente.getBody());

		Coche cocheModificado = respuestaExistente.getBody();
		cocheModificado.setModelo("CocheModificado");
		HttpEntity<Coche> entidadModificada = new HttpEntity<>(cocheModificado, headers);

		ResponseEntity<Coche> respuestaModificada = restTemplate.exchange(
				"http://localhost:" + port + "/api/v1/coche/" + idCocheExistente, HttpMethod.PUT, entidadModificada,
				Coche.class);

		assertEquals(HttpStatus.OK, respuestaModificada.getStatusCode());
		assertNotNull(respuestaModificada.getBody());
		assertEquals(cocheModificado.getModelo(), respuestaModificada.getBody().getModelo());
	}

	@Test
	public void pruebaEliminarCoche() {
		Long idCocheExistente = 1L;
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(obtenerTokenDeAutenticacion());
		HttpEntity<Void> requestEliminar = new HttpEntity<>(headers);

		ResponseEntity<Void> respuesta = restTemplate.exchange(
				"http://localhost:" + port + "/api/v1/coche/" + idCocheExistente, HttpMethod.DELETE, requestEliminar,
				Void.class);

		assertEquals(HttpStatus.OK, respuesta.getStatusCode());
	}
}
