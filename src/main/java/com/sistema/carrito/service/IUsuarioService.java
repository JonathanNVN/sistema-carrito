package com.sistema.carrito.service;

import java.util.Optional;

import com.sistema.carrito.models.Usuario;

public interface IUsuarioService {
	
	Optional<Usuario> findById(Integer id);
	Usuario save(Usuario usuario);
	Optional<Usuario> findByEmail(String email);

}
