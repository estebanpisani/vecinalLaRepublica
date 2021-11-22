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

import com.grupo9.vecinal.Entidades.Actividad;
import com.grupo9.vecinal.Entidades.Comercio;
import com.grupo9.vecinal.Entidades.Institucion;
import com.grupo9.vecinal.Entidades.Novedad;
import com.grupo9.vecinal.Entidades.Usuario;
import com.grupo9.vecinal.Servicios.ActividadServicio;
import com.grupo9.vecinal.Servicios.ComercioServicio;
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
	
	@Autowired
	private ComercioServicio comercioServ;
	
	@Autowired
	private ActividadServicio actividadServ;
	
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

	@GetMapping("/comercio/{id}")
	public ResponseEntity<byte[]> FotoComercio(@PathVariable Integer id) {
		try {
			Comercio comercio = comercioServ.buscarComercio(id);
			if (comercio.getFoto()==null) {
				throw new Exception("El usuario no posee foto");
			}
			byte[] foto = comercio.getFoto().getContenido();
			HttpHeaders cabecera = new HttpHeaders();
			cabecera.setContentType(MediaType.IMAGE_JPEG);
			return new ResponseEntity(foto, cabecera, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/actividad/{id}")
	public ResponseEntity<byte[]> FotoActividad(@PathVariable Integer id) {
		try {
			Actividad actividad = actividadServ.buscarActividad(id);
			if (actividad.getFoto()==null) {
				throw new Exception("El usuario no posee foto");
			}
			byte[] foto = actividad.getFoto().getContenido();
			HttpHeaders cabecera = new HttpHeaders();
			cabecera.setContentType(MediaType.IMAGE_JPEG);
			return new ResponseEntity(foto, cabecera, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
	}
	
}
