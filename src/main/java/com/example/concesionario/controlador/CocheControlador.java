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
@RequestMapping("/coches")
public class CocheControlador {

	@Autowired
	private CocheRepositorio cocheRepositorio;

	//Recupera todos los coches. Puede acceder cualquier rol
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@GetMapping
	public List<Coche> getAllCoches() {
		return cocheRepositorio.findAll();
	}

	//Recupera un coche por id. Puede acceder cualquier rol
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@GetMapping("/{id}")
	public Coche getCocheById(@PathVariable Long id) {
		return cocheRepositorio.findById(id).orElse(null);
	}

	//Añade un coche a la bbdd. Puede acceder solo el admin
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public Coche addCoche(@RequestBody Coche coche) {
		return cocheRepositorio.save(coche);
	}

	//Actualiza un coche de la bbdd. Puede acceder solo el admin
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

			//Una vez que se han introducido los parametros, se guarda
			return cocheRepositorio.save(cocheExistente);
		}
		return null;
	}

	//Borra un coche a la bbdd. Puede acceder solo el admin
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public void deleteCoche(@PathVariable Long id) {
		cocheRepositorio.deleteById(id);
	}

}