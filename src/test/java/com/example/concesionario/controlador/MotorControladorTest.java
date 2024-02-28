package com.example.concesionario.controlador;

import com.example.concesionario.ConcesionarioApplication;
import com.example.concesionario.entidad.Motor;
import com.example.concesionario.repositorio.MotorRepositorio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
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

	@Test
	public void pruebaObtenerTodosLosMotores() {
		ResponseEntity<Motor[]> respuesta = restTemplate.getForEntity("http://localhost:" + port + "/motores",
				Motor[].class);
		assertEquals(HttpStatus.OK, respuesta.getStatusCode());
		assertNotNull(respuesta.getBody());
	}

	@Test
	public void pruebaObtenerMotorPorId() {
		// Supongamos que hay un motor con ID 1 en la base de datos
		Long idMotorExistente = 1L;
		ResponseEntity<Motor> respuesta = restTemplate
				.getForEntity("http://localhost:" + port + "/motores/" + idMotorExistente, Motor.class);
		assertEquals(HttpStatus.OK, respuesta.getStatusCode());
		assertNotNull(respuesta.getBody());
		assertEquals(idMotorExistente, respuesta.getBody().getId());
	}

	@Test
	public void pruebaCrearMotor() {
		Motor nuevoMotor = new Motor(1L, "NuevoMotor", "Marca", 2022, "Gasolina", 8.0, 300000);
		ResponseEntity<Motor> respuesta = restTemplate.postForEntity("http://localhost:" + port + "/motores",
				nuevoMotor, Motor.class);
		assertEquals(HttpStatus.OK, respuesta.getStatusCode());
		assertNotNull(respuesta.getBody());
		assertEquals(nuevoMotor.getNombre(), respuesta.getBody().getNombre());

		// Limpieza: Elimina el motor creado durante la prueba
		motorRepositorio.deleteById(respuesta.getBody().getId());
	}

	@Test
	public void pruebaActualizarMotor() {
		// Supongamos que hay un motor con ID 1 en la base de datos
		Long idMotorExistente = 1L;
		ResponseEntity<Motor> respuestaExistente = restTemplate
				.getForEntity("http://localhost:" + port + "/motores/" + idMotorExistente, Motor.class);
		assertEquals(HttpStatus.OK, respuestaExistente.getStatusCode());
		assertNotNull(respuestaExistente.getBody());

		// Modifica el motor existente
		Motor motorModificado = respuestaExistente.getBody();
		motorModificado.setNombre("MotorModificado");
		HttpEntity<Motor> entidadModificada = new HttpEntity<>(motorModificado);
		ResponseEntity<Motor> respuestaModificada = restTemplate.exchange(
				"http://localhost:" + port + "/motores/" + idMotorExistente, HttpMethod.PUT, entidadModificada,
				Motor.class);

		assertEquals(HttpStatus.OK, respuestaModificada.getStatusCode());
		assertNotNull(respuestaModificada.getBody());
		assertEquals(motorModificado.getNombre(), respuestaModificada.getBody().getNombre());
	}

	@Test
	public void pruebaEliminarMotor() {
		// Supongamos que hay un motor con ID 1 en la base de datos
		Long idMotorExistente = 1L;

		// Intenta eliminar el motor
		ResponseEntity<Void> respuesta = restTemplate.exchange(
				"http://localhost:" + port + "/motores/" + idMotorExistente, HttpMethod.DELETE, null, Void.class);

		assertEquals(HttpStatus.OK, respuesta.getStatusCode());
	}
}