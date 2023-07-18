package com.sistema.carrito.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema.carrito.models.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer>{

}
