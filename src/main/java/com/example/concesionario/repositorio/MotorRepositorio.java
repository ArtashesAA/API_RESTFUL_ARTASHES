package com.example.concesionario.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.concesionario.entidad.Motor;

public interface MotorRepositorio extends JpaRepository<Motor, Long> {

}
