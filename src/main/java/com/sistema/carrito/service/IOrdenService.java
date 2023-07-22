package com.sistema.carrito.service;

import java.util.List;

import com.sistema.carrito.models.Orden;

public interface IOrdenService {
	
	List<Orden> findAll();
	
	Orden save(Orden orden);

}
