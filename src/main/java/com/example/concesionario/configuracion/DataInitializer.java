package com.example.concesionario.configuracion;

import java.util.List;

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

		Coche coche1 = new Coche((long) 1, "Bmw", "420d", 2019, 150, 35000, 1500, "Negro", 40000, "Coche deportivo");
		Coche coche2 = new Coche((long) 2, "Mercedes-Benz", "C200", 2018, 180, 40000, 1600, "Blanco", 45000,
				"Coche deportivo");
		Coche coche3 = new Coche((long) 3, "Ford", "Focus", 2020, 120, 25000, 1300, "Azul", 30000, "Coche compacto");
		Coche coche4 = new Coche((long) 4, "Volkswagen", "Golf", 2017, 200, 29000, 1400, "Gris", 35000,
				"Coche familiar");
		Coche coche5 = new Coche((long) 5, "Toyota", "Camry", 2021, 100, 30000, 1400, "Rojo", 38000, "Coche sedán");
		Coche coche6 = new Coche((long) 6, "Honda", "Civic", 2019, 160, 38000, 1500, "Plateado", 42000,
				"Coche compacto");
		Coche coche7 = new Coche((long) 7, "Audi", "A3", 2022, 80, 22000, 1200, "Negro", 35000, "Coche deportivo");
		Coche coche8 = new Coche((long) 8, "Chevrolet", "Malibu", 2016, 220, 42000, 1600, "Blanco", 40000,
				"Coche elegante");

		cocheRepositorio.saveAll(List.of(coche1, coche2, coche3, coche4, coche5, coche6, coche7, coche8));

		System.out.println("Base de datos de coches inicializada con más registros.");
	}
}