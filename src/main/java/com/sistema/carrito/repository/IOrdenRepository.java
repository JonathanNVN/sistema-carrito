package com.sistema.carrito.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema.carrito.models.Orden;
import com.sistema.carrito.models.Usuario;

@Repository
public interface IOrdenRepository extends JpaRepository<Orden, Integer>{
	
	List<Orden> findByUsuario(Usuario usuario);

}
