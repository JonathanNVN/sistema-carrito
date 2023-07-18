package com.sistema.carrito.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistema.carrito.models.Producto;
import com.sistema.carrito.repository.ProductoRepository;

@Service
public class ProductoServiceImplement implements ProductoService{

	@Autowired
	ProductoRepository productoRepository;
	
	@Override
	public Producto save(Producto producto) {		
		return productoRepository.save(producto);
	}

	@Override
	public Optional<Producto> get(Integer id) {
		return productoRepository.findById(id);
	}

	@Override
	public void update(Producto producto) {
		productoRepository.save(producto);
	}

	@Override
	public void delete(Integer id) {
		productoRepository.deleteById(id);
	}

}