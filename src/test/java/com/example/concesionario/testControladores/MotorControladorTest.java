package com.example.concesionario.testControladores;

import com.example.concesionario.ConcesionarioApplication;
import com.example.concesionario.entidad.Motor;
import com.example.concesionario.repositorio.MotorRepositorio;
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
public class MotorControladorTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private MotorRepositorio motorRepositorio;

	private String obtenerTokenDeAutenticacion() {
		// Token de usuario iniciado
		// **Caduca cada 24h
		return "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBleGFtcGxlLmNvbSIsImlhdCI6MTcwOTcyMjUzNCwiZXhwIjoxNzA5ODA4OTM0fQ.6DyDcQAhB-VYy6BJCm4bWALFVAmZ14hYwK2WkrNUsu8";
	}

	@Test
	public void pruebaObtenerTodosLosMotores() {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(obtenerTokenDeAutenticacion());
		HttpEntity<Void> request = new HttpEntity<>(headers);

		ResponseEntity<Motor[]> respuesta = restTemplate.exchange("http://localhost:" + port + "/api/v1/motor",
				HttpMethod.GET, request, Motor[].class);

		assertEquals(HttpStatus.OK, respuesta.getStatusCode());
		assertNotNull(respuesta.getBody());
	}

	@Test
	public void pruebaObtenerMotorPorId() {
		// Motor con ID 1
		Long idMotorExistente = 1L;
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(obtenerTokenDeAutenticacion());
		HttpEntity<Void> request = new HttpEntity<>(headers);

		ResponseEntity<Motor> respuesta = restTemplate.exchange(
				"http://localhost:" + port + "/api/v1/motor/" + idMotorExistente, HttpMethod.GET, request, Motor.class);

		assertEquals(HttpStatus.OK, respuesta.getStatusCode());
		assertNotNull(respuesta.getBody());
		assertEquals(idMotorExistente, respuesta.getBody().getId());
	}

	@Test
	public void pruebaCrearMotor() {
		Motor nuevoMotor = new Motor(1L, "NuevoMotor", "Marca", 2022, "Gasolina", 8.0, 300000);
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(obtenerTokenDeAutenticacion());
		HttpEntity<Motor> entidadMotor = new HttpEntity<>(nuevoMotor, headers);

		ResponseEntity<Motor> respuesta = restTemplate.exchange("http://localhost:" + port + "/api/v1/motor",
				HttpMethod.POST, entidadMotor, Motor.class);

		assertEquals(HttpStatus.OK, respuesta.getStatusCode());
		assertNotNull(respuesta.getBody());
		assertEquals(nuevoMotor.getNombre(), respuesta.getBody().getNombre());

		// Elimina el motor creado
		motorRepositorio.deleteById(respuesta.getBody().getId());
	}

	@Test
	public void pruebaActualizarMotor() {
		// Motor con ID 1
		Long idMotorExistente = 1L;
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(obtenerTokenDeAutenticacion());
		HttpEntity<Void> requestExistente = new HttpEntity<>(headers);

		ResponseEntity<Motor> respuestaExistente = restTemplate.exchange(
				"http://localhost:" + port + "/api/v1/motor/" + idMotorExistente, HttpMethod.GET, requestExistente,
				Motor.class);

		assertEquals(HttpStatus.OK, respuestaExistente.getStatusCode());
		assertNotNull(respuestaExistente.getBody());

		// Modifica el motor existente
		Motor motorModificado = respuestaExistente.getBody();
		motorModificado.setNombre("MotorModificado");
		HttpEntity<Motor> entidadModificada = new HttpEntity<>(motorModificado, headers);

		ResponseEntity<Motor> respuestaModificada = restTemplate.exchange(
				"http://localhost:" + port + "/api/v1/motor/" + idMotorExistente, HttpMethod.PUT, entidadModificada,
				Motor.class);

		assertEquals(HttpStatus.OK, respuestaModificada.getStatusCode());
		assertNotNull(respuestaModificada.getBody());
		assertEquals(motorModificado.getNombre(), respuestaModificada.getBody().getNombre());
	}

	@Test
	public void pruebaEliminarMotor() {
		// Motor con ID 1
		Long idMotorExistente = 1L;
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(obtenerTokenDeAutenticacion());
		HttpEntity<Void> requestEliminar = new HttpEntity<>(headers);

		// Intenta eliminar el motor
		ResponseEntity<Void> respuesta = restTemplate.exchange(
				"http://localhost:" + port + "/api/v1/motor/" + idMotorExistente, HttpMethod.DELETE, requestEliminar,
				Void.class);

		assertEquals(HttpStatus.OK, respuesta.getStatusCode());
	}
}
