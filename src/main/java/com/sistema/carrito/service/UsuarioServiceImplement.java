package com.sistema.carrito.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistema.carrito.models.Usuario;
import com.sistema.carrito.repository.IUsuarioRepository;

@Service
public class UsuarioServiceImplement implements IUsuarioService{
	
	@Autowired
	private IUsuarioRepository iUsuarioRepository;

	@Override
	public Optional<Usuario> findById(Integer id) {
		
		return iUsuarioRepository.findById(id);
	}

	@Override
	public Usuario save(Usuario usuario) {
		return iUsuarioRepository.save(usuario);
	}

	@Override
	public Optional<Usuario> findByEmail(String email) {
		return iUsuarioRepository.findByEmail(email);
	}



}
