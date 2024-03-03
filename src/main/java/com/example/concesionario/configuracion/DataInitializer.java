package com.example.concesionario.configuracion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.concesionario.entidad.Coche;
import com.example.concesionario.entidad.Motor;
import com.example.concesionario.entidad.Usuario;
import com.example.concesionario.repositorio.CocheRepositorio;
import com.example.concesionario.repositorio.MotorRepositorio;
import com.example.concesionario.repositorio.UsuarioRepositorio;

@Component
public class DataInitializer implements CommandLineRunner {

	@Autowired
	private final CocheRepositorio cocheRepositorio;

	@Autowired
	private final UsuarioRepositorio usuarioRepositorio;

	@Autowired
	private final PasswordEncoder passwordEncoder;

	@Autowired
	private final MotorRepositorio motorRepositorio;

	public DataInitializer(CocheRepositorio cocheRepositorio, UsuarioRepositorio usuarioRepositorio,
			PasswordEncoder passwordEncoder, MotorRepositorio motorRepositorio) {
		this.cocheRepositorio = cocheRepositorio;
		this.usuarioRepositorio = usuarioRepositorio;
		this.passwordEncoder = passwordEncoder;
		this.motorRepositorio = motorRepositorio;
	}

	@Override
	public void run(String... args) throws Exception {

		try {
			cocheRepositorio.deleteAll();
			usuarioRepositorio.deleteAll();
			motorRepositorio.deleteAll();

			// Coches
			Coche coche1 = new Coche(1L, "Bmw", "420d", 2019, 150, 35000, 1500, "Gasolina", "Negro", 40000,
					"Coche deportivo");
			Coche coche2 = new Coche(2L, "Mercedes-Benz", "C200", 2018, 180, 40000, 1600, "Gasolina", "Blanco", 45000,
					"Coche deportivo");
			Coche coche3 = new Coche(3L, "Ford", "Focus", 2020, 120, 25000, 1300, "Azul", "Diesel", 30000,
					"Coche compacto");
			Coche coche4 = new Coche(4L, "Volkswagen", "Golf", 2017, 200, 29000, 1400, "Gris", "Diesel", 35000,
					"Coche familiar");
			Coche coche5 = new Coche(5L, "Toyota", "Camry", 2021, 100, 30000, 1400, "Rojo", "Diesel", 38000,
					"Coche sedán");
			Coche coche6 = new Coche(6L, "Honda", "Civic", 2019, 160, 38000, 1500, "Plateado", "Gasolina", 42000,
					"Coche compacto");
			Coche coche7 = new Coche(7L, "Audi", "A3", 2022, 80, 22000, 1200, "Negro", "Diesel", 35000,
					"Coche deportivo");
			Coche coche8 = new Coche(8L, "Chevrolet", "Malibu", 2016, 220, 42000, 1600, "Blanco", "Gasolina", 40000,
					"Coche elegante");

			cocheRepositorio.saveAll(List.of(coche1, coche2, coche3, coche4, coche5, coche6, coche7, coche8));

			// Motores
			Motor motor1 = new Motor(1L, "OM651", "Mercedes-Benz", 2019, "Gasolina", 5.5, 400000);
			Motor motor2 = new Motor(2L, "B47", "BMW", 2018, "Gasolina", 6.0, 350000);
			Motor motor3 = new Motor(3L, "K20C2", "Honda", 2020, "Gasolina", 7.5, 250000);
			Motor motor4 = new Motor(4L, "V12", "Ferrari", 2021, "Gasolina", 15.0, 200000);

			motorRepositorio.saveAll(List.of(motor1, motor2, motor3, motor4));

			// Usuarios
			Usuario usuario1 = new Usuario();
			usuario1.setId(1);
			usuario1.setEmail("usuario1@example.com");
			usuario1.setPassword(passwordEncoder.encode("password1"));
			usuario1.setRole("USER");

			Usuario usuario2 = new Usuario();
			usuario2.setId(2);
			usuario2.setEmail("admin@example.com");
			usuario2.setPassword(passwordEncoder.encode("admin"));
			usuario2.setRole("ADMIN");

			usuarioRepositorio.saveAll(List.of(usuario1, usuario2));

			System.out.println("Base de datos de coches inicializada con más registros.");
		} catch (Exception e) {
			// Manejo de excepciones
			System.err.println("Error durante la inicialización de datos: " + e.getMessage());
			e.printStackTrace();
		}
	}
}