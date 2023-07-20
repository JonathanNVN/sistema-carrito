package com.sistema.carrito.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sistema.carrito.models.DetalleOrden;
import com.sistema.carrito.models.Orden;
import com.sistema.carrito.models.Producto;
import com.sistema.carrito.service.ProductoService;

@Controller
@RequestMapping("/")
public class HomeController {

	private final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private ProductoService productoService;
	
	List<DetalleOrden> detalles = new ArrayList<DetalleOrden>(); //Almacenamos en una lista los detalles de la orden.
	
	Orden orden = new Orden(); //Datos de la orden.
	
	@GetMapping("")
	public String home(Model model) {
		
		model.addAttribute("productos", productoService.findAll());
		return "usuario/home";
	}
	
	@GetMapping("productoHome/{id}")
	public String productoHome(@PathVariable Integer id, Model model) {
		
		LOGGER.info("Id del producto enviado como parametro {}", id);
		
		Producto producto = new Producto();
		Optional<Producto> productoOptional = productoService.get(id);
		producto = productoOptional.get();
		
		model.addAttribute("producto", producto);
		
		return "usuario/productoHome";
	}
	
	@PostMapping("/carrito")
	public String agregaCarrito(@RequestParam Integer id, @RequestParam Integer cantidad) {
		
		DetalleOrden detalleOrden = new DetalleOrden();
		Producto producto = new Producto();
		
		double sumTotal = 0;
		
		Optional<Producto> optionalProducto = productoService.get(id);
		LOGGER.info("Producto añadido: {}", optionalProducto.get());
		LOGGER.info("Cantidad: {}", cantidad);
		return "usuario/carrito";
	}
	
}
