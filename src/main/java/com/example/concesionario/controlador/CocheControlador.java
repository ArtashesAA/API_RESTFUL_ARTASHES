package com.example.concesionario.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.concesionario.entidad.Coche;
import com.example.concesionario.repositorio.CocheRepositorio;

@RestController
@RequestMapping("/api/v1/coche")
public class CocheControlador {

	@Autowired
	private CocheRepositorio cocheRepositorio;

	/*
	 * Recupera todos los coches. Puede acceder cualquier rol
	 * @return recupera todos los coches
	 */
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@GetMapping
	public List<Coche> getAllCoches() {
		return cocheRepositorio.findAll();
	}

	/*
	 * Recupera un coche por id. Puede acceder cualquier rol
	 * @Parameter id de coche que se va a buscar
	 * @return recupera el coche por id
	 */
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@GetMapping("/{id}")
	public Coche getCocheById(@PathVariable Long id) {
		return cocheRepositorio.findById(id).orElse(null);
	}

	/*
	 * Añade un coche a la bbdd. Puede acceder solo el admin
	 * @Parameter coche que se va a añadir
	 * @return guarda el coche pasado por parámetro
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public Coche addCoche(@RequestBody Coche coche) {
		return cocheRepositorio.save(coche);
	}

	
	/*
	 * Actualiza un coche de la bbdd. Puede acceder solo el admin
	 * @Parameter id del coche que se quiere actualizar
	 * @Parameter nuevoCoche que contiene los datos del coche nuevo que va a sustituir al otro
	 * @return actualiza el coche pasado por parámetro 
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public Coche updateCoche(@PathVariable Long id, @RequestBody Coche nuevoCoche) {
		Coche cocheExistente = cocheRepositorio.findById(id).orElse(null);
		if (cocheExistente != null) {
			cocheExistente.setMarca(nuevoCoche.getMarca());
			cocheExistente.setModelo(nuevoCoche.getModelo());
			cocheExistente.setAnyo(nuevoCoche.getAnyo());
			cocheExistente.setKilometraje(nuevoCoche.getKilometraje());
			cocheExistente.setPeso(nuevoCoche.getPeso());
			cocheExistente.setColor(nuevoCoche.getColor());
			cocheExistente.setPrecio(nuevoCoche.getPrecio());
			cocheExistente.setDescripcion(nuevoCoche.getDescripcion());

			return cocheRepositorio.save(cocheExistente);
		}
		return null;
	}

	
	/*
	 * Borra un coche a la bbdd. Puede acceder solo el admin
	 * @Parameter id del coche que se quiere borrar
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public void deleteCoche(@PathVariable Long id) {
		cocheRepositorio.deleteById(id);
	}

}