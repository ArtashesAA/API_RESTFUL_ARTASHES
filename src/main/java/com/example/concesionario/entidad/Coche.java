package com.example.concesionario.entidad;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Coche {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "La marca no puede estar en blanco")
	private String marca;

	@NotBlank(message = "El modelo no puede estar en blanco")
	private String modelo;

	@NotNull(message = "El año no puede ser nulo")
	private Integer anyo;

	@NotNull(message = "Los kilómetros no pueden ser nulos")
	private Integer kilometros;

	public Coche() {

	}

	public Coche(Long id, String marca, String modelo, Integer anyo, Integer kilometros) {
		this.id = id;
		this.marca = marca;
		this.modelo = modelo;
		this.anyo = anyo;
		this.kilometros = kilometros;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public Integer getAnyo() {
		return anyo;
	}

	public void setAnyo(Integer anyo) {
		this.anyo = anyo;
	}

	public Integer getKilometros() {
		return kilometros;
	}

	public void setKilometros(Integer kilometros) {
		this.kilometros = kilometros;
	}

	@Override
	public String toString() {
		return "Coche [getId()=" + getId() + ", getMarca()=" + getMarca() + ", getModelo()=" + getModelo()
				+ ", getAnyo()=" + getAnyo() + ", getKilometros()=" + getKilometros() + "]";
	}
}
