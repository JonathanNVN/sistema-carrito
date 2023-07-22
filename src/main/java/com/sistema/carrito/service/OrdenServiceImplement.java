package com.sistema.carrito.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistema.carrito.models.Orden;
import com.sistema.carrito.repository.IOrdenRepository;

@Service
public class OrdenServiceImplement implements IOrdenService{

	@Autowired
	private IOrdenRepository iOrdenRepository;
	
	@Override
	public Orden save(Orden orden) {
		return iOrdenRepository.save(orden);
	}

}
