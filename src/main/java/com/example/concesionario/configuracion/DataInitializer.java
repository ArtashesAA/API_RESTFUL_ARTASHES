package com.example.concesionario.configuracion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.concesionario.entidad.Coche;
import com.example.concesionario.repositorio.CocheRepositorio;

@Component
public class DataInitializer implements CommandLineRunner {

	@Autowired
	private final CocheRepositorio cocheRepositorio;

	public DataInitializer(CocheRepositorio cocheRepositorio) {
		this.cocheRepositorio = cocheRepositorio;
	}

	@Override
	public void run(String... args) throws Exception {

		Coche coche1 = new Coche((long) 1, "Toyota", "Corolla", 2019, 15000);
		Coche coche2 = new Coche((long) 2, "Honda", "Civic", 2018, 18000);
		Coche coche3 = new Coche((long) 3, "Ford", "Focus", 2020, 12000);
		Coche coche4 = new Coche((long) 4, "Volkswagen", "Golf", 2017, 20000);

		cocheRepositorio.save(coche1);
		cocheRepositorio.save(coche2);
		cocheRepositorio.save(coche3);
		cocheRepositorio.save(coche4);

		System.out.println("Base de datos de coches inicializada.");
	}
}