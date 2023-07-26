package com.sistema.carrito.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import com.sistema.carrito.models.Usuario;
import com.sistema.carrito.repository.IOrdenRepository;
import com.sistema.carrito.service.IDetalleOrdenService;
import com.sistema.carrito.service.IOrdenService;
import com.sistema.carrito.service.IUsuarioService;
import com.sistema.carrito.service.ProductoService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class HomeController {

	private final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private IOrdenService iOrdenService;
	
	@Autowired
	private IDetalleOrdenService detalleOrdenService;
	
	@Autowired
	private IUsuarioService iUsuarioService;
	
	@Autowired
	private ProductoService productoService;	
	
	List<DetalleOrden> detalles = new ArrayList<DetalleOrden>(); //Almacenamos en una lista los detalles de la orden.
	
	Orden orden = new Orden(); //Datos de la orden.
	
	@GetMapping("")
	public String home(Model model, HttpSession session) {
		
		LOGGER.info("Sesion del usuario: {}", session.getAttribute("idUsuario"));
		
		model.addAttribute("productos", productoService.findAll());
		
		model.addAttribute("sesion", session.getAttribute("idUsuario"));
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
	public String agregaCarrito(@RequestParam Integer id, @RequestParam Integer cantidad, Model model) {
		
		DetalleOrden detalleOrden = new DetalleOrden();
		Producto producto = new Producto();
		
		double sumTotal = 0;
		
		Optional<Producto> optionalProducto = productoService.get(id);
		
		LOGGER.info("Producto añadido: {}", optionalProducto.get());
		LOGGER.info("Cantidad: {}", cantidad);
		
		producto = optionalProducto.get();
		
		detalleOrden.setCantidad(cantidad);
		detalleOrden.setPrecio(producto.getPrecio());
		detalleOrden.setNombre(producto.getNombre());
		detalleOrden.setTotal(producto.getPrecio() * cantidad);
		detalleOrden.setProductos(producto);
		
		//Validamos que el producto no se agregue mas de una vez.
		Integer idProducto = producto.getId();
		boolean productoAgregado = detalles.stream().anyMatch(p -> p.getProductos().getId() == idProducto);
		
			if(!productoAgregado) {
				detalles.add(detalleOrden);
			}else {System.out.println("añadiste el mismo producto");}	
		//------------------------------------------------------------------------------------------------
			
		sumTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();
		
		orden.setTotal(sumTotal);
		
		model.addAttribute("carrito", detalles);
		model.addAttribute("orden", orden);
		
		return "usuario/carrito";
	}
	
	@GetMapping("/delete/carrito/{id}")
	public String eliminarProductoCarrito(@PathVariable Integer id, Model model) {
		
		List<DetalleOrden> ordenNueva = new ArrayList<DetalleOrden>();
		
		for(DetalleOrden detalleOrden: detalles) {
			
			if(detalleOrden.getProductos().getId()!= id) {
				ordenNueva.add(detalleOrden);
			}
		}
		
		detalles = ordenNueva; //Colocar la nueva lista con los productos restantes.
		
		double sumaTotal = 0;
		sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();
		
		orden.setTotal(sumaTotal);
		model.addAttribute("carrito", detalles);
		model.addAttribute("orden", orden);
		
		return "usuario/carrito";
	}
	
	@GetMapping("/getCarrito")
	public String getCarrito(Model model, HttpSession session) {
		
		model.addAttribute("carrito", detalles);
		model.addAttribute("orden", orden);
		
		model.addAttribute("sesion", session.getAttribute("idUsuario"));
		return "/usuario/carrito";
	}
	
	@GetMapping("/orden")
	public String order(Model model, HttpSession session) {
		
		Usuario usuario = iUsuarioService.findById(Integer.parseInt(session.getAttribute("idUsuario").toString())).get();
		
		model.addAttribute("carrito", detalles);
		model.addAttribute("orden", orden);
		model.addAttribute("usuario", usuario);
		
		return "/usuario/resumenorden";
	}
	
	//Guardar la orden.
	@GetMapping("/saveOrden")
	public String saveOrden(HttpSession session) {
		
		Date fechaCreacion = new Date();
		
		orden.setFechaCreacion(fechaCreacion);
		orden.setNumero(iOrdenService.generarNumeroOrden());
		
		Usuario usuario = iUsuarioService.findById(Integer.parseInt(session.getAttribute("idUsuario").toString())).get();
		
		orden.setUsuario(usuario);
		iOrdenService.save(orden);
		
		//Guardando detalles.
		for(DetalleOrden dt:detalles) {
			
			dt.setOrden(orden);
			detalleOrdenService.save(dt);
		}
		
		//Limpiar lista y ordenes.
		orden = new Orden();
		detalles.clear();
		
		return "redirect:/";
	}
	
	@PostMapping("/search")
	public String searchProduct(@RequestParam String nombre, Model model) {
		LOGGER.info("Nombre del producto: {}", nombre);
		List<Producto> productos = productoService.findAll().stream().filter(p -> p.getNombre().contains(nombre)).collect(Collectors.toList());
		model.addAttribute("productos", productos);
		return "/usuario/home";
	}
}
