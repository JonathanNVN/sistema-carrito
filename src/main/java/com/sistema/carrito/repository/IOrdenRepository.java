package com.sistema.carrito.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema.carrito.models.Orden;

@Repository
public interface IOrdenRepository extends JpaRepository<Orden, Integer>{

}
