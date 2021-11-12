package com.grupo9.vecinal.Controladores;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.grupo9.vecinal.Entidades.Usuario;
import com.grupo9.vecinal.Servicios.UsuarioServicio;

@Controller
@RequestMapping("/foto")
public class FotoControlador {

	@Autowired
	private UsuarioServicio usuarioServ;

	@GetMapping("/usuario/{id}")
	public ResponseEntity<byte[]> FotoUsuario(@PathVariable Integer id) {
		try {
			Usuario usuario = usuarioServ.buscarUsuario(id);
			if (usuario.getFoto()==null) {
				throw new Exception("El usuario no posee foto");
			}
			byte[] foto = usuario.getFoto().getContenido();
			HttpHeaders cabecera = new HttpHeaders();
			cabecera.setContentType(MediaType.IMAGE_JPEG);
			return new ResponseEntity(foto, cabecera, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
	}
}
