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

import com.grupo9.vecinal.Entidades.Institucion;
import com.grupo9.vecinal.Entidades.Novedad;
import com.grupo9.vecinal.Entidades.Usuario;
import com.grupo9.vecinal.Servicios.InstitucionServicio;
import com.grupo9.vecinal.Servicios.NovedadServicio;
import com.grupo9.vecinal.Servicios.UsuarioServicio;

@Controller
@RequestMapping("/foto")
public class FotoControlador {

	@Autowired
	private UsuarioServicio usuarioServ;
	
	@Autowired
	private NovedadServicio novedadServ;

	@Autowired
	private InstitucionServicio institucionServ;
	
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
	
	@GetMapping("/novedad/{id}")
	public ResponseEntity<byte[]> FotoNovedades(@PathVariable Integer id) {
		try {
			Novedad novedad = novedadServ.mostrarNovedad(id);
			if (novedad.getFoto()==null) {
				throw new Exception("El usuario no posee foto");
			}
			byte[] foto = novedad.getFoto().getContenido();
			HttpHeaders cabecera = new HttpHeaders();
			cabecera.setContentType(MediaType.IMAGE_JPEG);
			return new ResponseEntity(foto, cabecera, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/institucion/{id}")
	public ResponseEntity<byte[]> FotoInstitucion(@PathVariable Integer id) {
		try {
			Institucion institucion = institucionServ.mostrarInstitucion(id);
			if (institucion.getFoto()==null) {
				throw new Exception("El usuario no posee foto");
			}
			byte[] foto = institucion.getFoto().getContenido();
			HttpHeaders cabecera = new HttpHeaders();
			cabecera.setContentType(MediaType.IMAGE_JPEG);
			return new ResponseEntity(foto, cabecera, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
	}
	
}
