package com.example.concesionario.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/")
public class CocheControlador {

	@Autowired
	private CocheRepositorio cocheRepositorio;
	
	@GetMapping
    public List<Coche> getAllCoches() {
        return cocheRepositorio.findAll();
    }

    @GetMapping("/{id}")
    public Coche getCocheById(@PathVariable Long id) {
        return cocheRepositorio.findById(id).orElse(null);
    }

    @PostMapping
    public Coche addCoche(@RequestBody Coche coche) {
        return cocheRepositorio.save(coche);
    }

    @PutMapping("/{id}")
    public Coche updateCoche(@PathVariable Long id, @RequestBody Coche nuevoCoche) {
        Coche cocheExistente = cocheRepositorio.findById(id).orElse(null);
        if (cocheExistente != null) {
        	cocheExistente.setMarca(nuevoCoche.getMarca());
        	cocheExistente.setModelo(nuevoCoche.getModelo());
        	cocheExistente.setAnyo(nuevoCoche.getAnyo());
        	cocheExistente.setKilometros(nuevoCoche.getKilometros());
            return cocheRepositorio.save(cocheExistente);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteCoche(@PathVariable Long id) {
        cocheRepositorio.deleteById(id);
    }
}
