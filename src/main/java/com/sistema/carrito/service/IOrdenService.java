package com.sistema.carrito.service;

import java.util.List;

import com.sistema.carrito.models.Orden;
import com.sistema.carrito.models.Usuario;

public interface IOrdenService {
	
	List<Orden> findAll();
	
	Orden save(Orden orden);
	
	String generarNumeroOrden();
	
	List<Orden> findByUsuario(Usuario usuario);

}
