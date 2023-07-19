package com.sistema.carrito.service;

import java.io.File;
import java.io.IOException;
import java.nio.channels.NonReadableChannelException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class uploadImagenService {

	private String folder="images//";
	
	public String guardarImagen(MultipartFile file) throws IOException {
		
		if(!file.isEmpty()) {
			byte [] bytes = file.getBytes(); //Convertimos la imagen a bytes.
			Path path = Paths.get(folder + file.getOriginalFilename());
			Files.write(path, bytes);
			return file.getOriginalFilename();
		}
		return "default.jpg";
	}
	
	public void eliminarImagen(String nombre) {
		
		String ruta = "images//";
		File file = new File(ruta + nombre);
		file.delete();
	}
}
