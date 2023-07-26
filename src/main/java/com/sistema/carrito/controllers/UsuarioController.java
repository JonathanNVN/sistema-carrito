package com.sistema.carrito.controllers;

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

import com.sistema.carrito.models.Orden;
import com.sistema.carrito.models.Usuario;
import com.sistema.carrito.service.IOrdenService;
import com.sistema.carrito.service.IUsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	private final Logger LOGGER = LoggerFactory.getLogger(UsuarioController.class);
	
	@Autowired
	private IUsuarioService iUsuarioService;
	
	@Autowired
	private IOrdenService iOrdenService;
	
	@GetMapping("/registro")
	public String create() {
		return "usuario/registro";
	}
	
	@PostMapping("/save")
	public String save(Usuario usuario) {
		
		LOGGER.info("Usuario registrado: {}", usuario);
		
		usuario.setTipo("USER");
		iUsuarioService.save(usuario);
		
		return "redirect:/";
	}
	
	@GetMapping("/login")
	public String login() {
		return "usuario/login";
	}
	
	@PostMapping("/acceder")
	public String acceder(Usuario usuario, HttpSession session) {
		
		LOGGER.info("Accesos: {}", usuario);
	
		Optional<Usuario> user = iUsuarioService.findByEmail(usuario.getEmail());
		
		//LOGGER.info("Usuario de la BD: {}", user.get());
		
			if(user.isPresent()) {
				
				session.setAttribute("idUsuario", user.get().getId());
				
					if(user.get().getTipo().equals("ADMIN")) {					
						return "redirect:/administrador";
						
					}else {					
						return "redirect:/";
					}
				
			}else {				
				LOGGER.info("Este usuario no existe.");
			}
		
		return "redirect:/";
	}
	
	@GetMapping("/compras")
	public String obtenerCompras(Model model, HttpSession session) {
		
		model.addAttribute("sesion", session.getAttribute("idUsuario"));
		
		Usuario usuario = iUsuarioService.findById(Integer.parseInt(session.getAttribute("idUsuario").toString())).get();
		List<Orden> ordenes = iOrdenService.findByUsuario(usuario);
		
		model.addAttribute("ordenes", ordenes);
		
		return "usuario/compras";
	}
	
	@GetMapping("/detalles/{id}")
	public String obtenerDetalleCompras(@PathVariable Integer id, Model model, HttpSession session) {
		
		
		LOGGER.info("Id de la orden: {}", id);
		
		Optional<Orden> orden = iOrdenService.findById(id);
		
		model.addAttribute("detalles", orden.get().getDetalle());
		
		//Sesion
		model.addAttribute("sesion", session.getAttribute("idUsuario"));	
		
		return "usuario/detallecompra";
	}
	
	@GetMapping("/cerrar")
	public String cerrarSesion(HttpSession session) {
		
		session.removeAttribute("idUsuario");
		return "redirect:/";
	}

}
