package com.example.concesionario.entidad;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Motor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@NotBlank(message = "El nombre no puede estar en blanco")
	@Column(name = "nombre", nullable = false)
	private String nombre;

	@NotBlank(message = "El fabricante no puede estar en blanco")
	@Column(name = "fabricante", nullable = false)
	private String fabricante;

	@NotNull(message = "El año de fabricación no puede ser nulo")
	@Column(name = "anyoFabricacion", nullable = false)
	private Integer anyoFabricacion;

	@NotBlank(message = "El tipo de combustible no puede estar en blanco")
	@Column(name = "tipoCombustible", nullable = false)
	private String tipoCombustible;

	@NotNull(message = "El consumo no puede ser nulo")
	@Column(name = "consumo", nullable = false)
	private Double consumo;

	@NotNull(message = "La vida útil no puede ser nula")
	@Column(name = "vidaUtil", nullable = false)
	private Integer vidaUtil;


	public Motor() {

	}

	public Motor(Long id, @NotBlank(message = "El nombre no puede estar en blanco") String nombre,
			@NotBlank(message = "El fabircante no puede estar en blanco") String fabricante,
			@NotNull(message = "El anyo de fabricacion no puede ser nulo") Integer anyoFabricacion,
			@NotBlank(message = "El tipo de combustible no puede estar ser nulo") String tipoCombustible,
			@NotNull(message = "El consumo no puede ser nulo") Double consumo,
			@NotNull(message = "La vida util no puede ser nula") Integer vidaUtil) {
		this.id = id;
		this.nombre = nombre;
		this.fabricante = fabricante;
		this.anyoFabricacion = anyoFabricacion;
		this.tipoCombustible = tipoCombustible;
		this.consumo = consumo;
		this.vidaUtil = vidaUtil;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getAnyoFabricacion() {
		return anyoFabricacion;
	}

	public void setAnyoFabricacion(Integer anyoFabricacion) {
		this.anyoFabricacion = anyoFabricacion;
	}

	public Double getConsumo() {
		return consumo;
	}

	public void setConsumo(Double consumo) {
		this.consumo = consumo;
	}

	public Integer getVidaUtil() {
		return vidaUtil;
	}

	public void setVidaUtil(Integer vidaUtil) {
		this.vidaUtil = vidaUtil;
	}

	public String getFabricante() {
		return fabricante;
	}

	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}

	public String getTipoCombustible() {
		return tipoCombustible;
	}

	public void setTipoCombustible(String tipoCombustible) {
		this.tipoCombustible = tipoCombustible;
	}

	@Override
	public String toString() {
		return "Motor [getId()=" + getId() + ", getNombre()=" + getNombre() + ", getAnyoFabricacion()="
				+ getAnyoFabricacion() + ", getConsumo()=" + getConsumo() + ", getVidaUtil()=" + getVidaUtil()
				+ ", getFabricante()=" + getFabricante() + ", getTipoCombustible()=" + getTipoCombustible() + "]";
	}

}
