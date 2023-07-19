package com.sistema.carrito.controllers;


import java.io.IOException;
import java.util.Optional;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sistema.carrito.models.Producto;
import com.sistema.carrito.models.Usuario;
import com.sistema.carrito.service.ProductoService;
import com.sistema.carrito.service.uploadImagenService;



@Controller
@RequestMapping("/productos")
public class ProductoController {
	
	private final Logger LOGGER = (Logger) LoggerFactory.getLogger(ProductoController.class);

	@Autowired
	ProductoService productoService;
	
	@Autowired
	private uploadImagenService upload;
	
	@GetMapping("")
	public String show(Model model) {
		
		model.addAttribute("productos", productoService.findAll());
		return "productos/show";
	}
	
	@GetMapping("/create")
	public String create(){
		return "/productos/create";
	}
	
	@PostMapping("/save")
	public String save(Producto producto, @RequestParam("img") MultipartFile file) throws IOException{
		
		LOGGER.info("Este es el objeto producto {}", producto);
		
		Usuario usuario = new Usuario(1,"","","","","","","");
		producto.setUsuario(usuario);		
		
		if(producto.getId() == null) { //Cuando se crea un producto.
			
			String nombreImagen = upload.guardarImagen(file);
			producto.setImagen(nombreImagen);
			
		}else {
			
			if(file.isEmpty()) { //Cuando editamos el producto pero no cambiamos la imagen.
				
				Producto p = new Producto();
				p = productoService.get(producto.getId()).get();
				producto.setImagen(p.getImagen());
				
			}else {
			 
				String nombreImagen = upload.guardarImagen(file);
				producto.setImagen(nombreImagen);			 
			 
			}
		}
		
		productoService.save(producto);	
		
		return "redirect:/productos";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable Integer id, Model model) {
		
		Producto producto = new Producto();
		
		Optional<Producto> optionalProductOptional = productoService.get(id);
		
		producto = optionalProductOptional.get();
		
		LOGGER.info("Producto buscado: {}", producto);
		
		model.addAttribute("producto", producto);
		
		return "productos/edit";
	}
	
	@PostMapping("/update")
	public String update(Producto producto) {
		
		productoService.update(producto);
		return "redirect:/productos";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Integer id) {
		
		productoService.delete(id);
		return "redirect:/productos";
	}
}
