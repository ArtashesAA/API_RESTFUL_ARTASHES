package com.example.concesionario.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.concesionario.entidad.Motor;
import com.example.concesionario.repositorio.MotorRepositorio;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/motor")
public class MotorControlador {

	@Autowired
	private MotorRepositorio motorRepositorio;

	/*
	 * Recupera todos los motores. Puede acceder cualquier rol
	 * 
	 * @return recupera todos los motores
	 */
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@GetMapping
	public ResponseEntity<List<Motor>> obtenerTodosLosMotores() {
		List<Motor> motores = motorRepositorio.findAll();
		return new ResponseEntity<>(motores, HttpStatus.OK);
	}

	/*
	 * Recupera un motor por id. Puede acceder cualquier rol
	 * 
	 * @Parameter id de motor que se va a buscar
	 * 
	 * @return recupera el motor por id
	 */
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@GetMapping("/{id}")
	public ResponseEntity<Motor> obtenerMotorPorId(@PathVariable Long id) {
		return motorRepositorio.findById(id).map(motor -> new ResponseEntity<>(motor, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/*
	 * Añade un motor a la bbdd. Puede acceder solo el admin
	 * 
	 * @Parameter motor que se va a añadir
	 * 
	 * @return guarda el motor pasado por parámetro
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<Motor> crearMotor(@Valid @RequestBody Motor motor) {
		Motor nuevoMotor = motorRepositorio.save(motor);
		return new ResponseEntity<>(nuevoMotor, HttpStatus.CREATED);
	}

	/*
	 * Actualiza un motor de la bbdd. Puede acceder solo el admin
	 * 
	 * @Parameter id del motor que se quiere actualizar
	 * 
	 * @Parameter motorActualizado que contiene los datos del motor nuevo que va a
	 * sustituir al otro
	 * 
	 * @return actualiza el motor pasado por parámetro
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<Motor> actualizarMotor(@PathVariable Long id, @Valid @RequestBody Motor motorActualizado) {
		return motorRepositorio.findById(id).map(motorExistente -> {
			motorExistente.setNombre(motorActualizado.getNombre());
			motorExistente.setAnyoFabricacion(motorActualizado.getAnyoFabricacion());
			motorExistente.setConsumo(motorActualizado.getConsumo());
			motorExistente.setVidaUtil(motorActualizado.getVidaUtil());

			Motor motorActualizadoResult = motorRepositorio.save(motorExistente);
			return new ResponseEntity<>(motorActualizadoResult, HttpStatus.OK);
		}).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/*
	 * Borra un motor a la bbdd. Puede acceder solo el admin
	 * 
	 * @Parameter id del motor que se quiere borrar
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminarMotor(@PathVariable Long id) {
		try {
			motorRepositorio.deleteById(id);
			return new ResponseEntity<>("Motor con ID " + id + " borrado correctamente", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Error al borrar el motor con ID " + id, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
