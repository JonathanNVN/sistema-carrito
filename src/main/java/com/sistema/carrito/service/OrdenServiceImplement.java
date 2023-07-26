package com.sistema.carrito.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistema.carrito.models.Orden;
import com.sistema.carrito.models.Usuario;
import com.sistema.carrito.repository.IOrdenRepository;

@Service
public class OrdenServiceImplement implements IOrdenService{

	@Autowired
	private IOrdenRepository iOrdenRepository;
	
	@Override
	public Orden save(Orden orden) {
		return iOrdenRepository.save(orden);
	}

	@Override
	public List<Orden> findAll() {
		return iOrdenRepository.findAll();
	}
	
	public String generarNumeroOrden () {
		
		int numero = 0;
		String numConcat = "";
		
		List<Orden> ordenes = findAll();
		
		List<Integer> numeros = new ArrayList<Integer>();
		
		ordenes.stream().forEach(o -> numeros.add(Integer.parseInt(o.getNumero())));
		
			if(ordenes.isEmpty()) {
				numero = 1;
			}else {
				numero = numeros.stream().max(Integer::compare).get();
				numero++;
			}
			
			if(numero < 10) {
				numConcat = "000000000" + String.valueOf(numero);
			}else if(numero < 100) {
				numConcat = "00000000" + String.valueOf(numero);
			}else if (numero < 1000) {
				numConcat = "00000000" + String.valueOf(numero);
			}else if (numero < 10000) {
				numConcat = "00000000" + String.valueOf(numero);
			} //...
		
		return numConcat;
	}

	@Override
	public List<Orden> findByUsuario(Usuario usuario) {
		return iOrdenRepository.findByUsuario(usuario);
	}

	@Override
	public Optional<Orden> findById(Integer id) {
		return iOrdenRepository.findById(id);
	}

}
