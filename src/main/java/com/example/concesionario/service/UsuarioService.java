package com.example.concesionario.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.concesionario.repositorio.UsuarioRepositorio;

@Service
public class UsuarioService implements UserDetailsService {

	@Autowired
	private UsuarioRepositorio ourUserRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return ourUserRepo.findByEmail(username).orElseThrow();
	}
}
