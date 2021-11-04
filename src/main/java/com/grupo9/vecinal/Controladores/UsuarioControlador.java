package com.grupo9.vecinal.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.grupo9.vecinal.Servicios.UsuarioServicio;

@Controller
@RequestMapping("/usuarios")
public class UsuarioControlador {

	@Autowired
	private UsuarioServicio usuarioServ;

	@GetMapping("/registro")
	public String registro() {
		return "registro.html";
	}

	@PostMapping("/registro")
	public String registro(ModelMap modelo, @RequestParam String nombreUsuario, @RequestParam String contrasenia,
			@RequestParam String contrasenia2, @RequestParam String emailUsuario, @RequestParam String nombre,
			@RequestParam String apellido, @RequestParam(required = false) Integer telefono) {

		try {
			usuarioServ.crearUsuario(nombreUsuario, contrasenia, contrasenia2, emailUsuario, nombre, apellido,
					telefono);
			return "redirect:/";
		} catch (Exception e) {
			modelo.put("error", e.getMessage());
			modelo.put("nombreUsuario", nombreUsuario);
			modelo.put("contrasenia", contrasenia);
			modelo.put("contrasenia2", contrasenia2);
			modelo.put("emailUsuario", emailUsuario);
			modelo.put("nombre", nombre);
			modelo.put("apellido", apellido);
			modelo.put("telefono", telefono);

			return "registro.html";
		}
		
	}

	
	@PostMapping("/modificar")
	public String modificarUsuario(ModelMap modelo, @RequestParam String nombreUsuario,
			@RequestParam String emailUsuario, @RequestParam String nombre, @RequestParam String apellido,
			@RequestParam Integer telefono, @RequestParam Integer id) {

		try {
			usuarioServ.modificarUsuario(nombreUsuario, emailUsuario, nombre, apellido, telefono, id);
			return "usuarios.html";
		} catch (Exception e) {
			modelo.put("error", e.getMessage());
			return "redirect:/usuarios/registro";
		}

	}
	
	@GetMapping("/inscripcion")
	public String inscripcion() {
		return "inscripcion_back.html";
	}
	
	@PostMapping("/inscribir")
	public String inscribir(@RequestParam Integer idUsuario, @RequestParam Integer idActividad) {
		try {
			usuarioServ.inscripcionActividad(idUsuario, idActividad);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		return "inscripcion_back.html";
	}
	
	
}
