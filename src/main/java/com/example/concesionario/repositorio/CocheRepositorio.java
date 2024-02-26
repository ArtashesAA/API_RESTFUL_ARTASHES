package com.example.concesionario.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.concesionario.entidad.Coche;

public interface CocheRepositorio extends JpaRepository<Coche, Long>{

}
