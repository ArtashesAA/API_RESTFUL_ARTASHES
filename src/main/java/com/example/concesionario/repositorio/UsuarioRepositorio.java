package com.example.concesionario.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.concesionario.entidad.Usuario;

import java.util.Optional;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByEmail(String email);
}
