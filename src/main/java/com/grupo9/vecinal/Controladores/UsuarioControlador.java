package com.grupo9.vecinal.Controladores;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.grupo9.vecinal.Entidades.Usuario;
import com.grupo9.vecinal.Servicios.UsuarioServicio;

@Controller
@RequestMapping("/usuarios")
public class UsuarioControlador {

	@Autowired
	private UsuarioServicio usuarioServ;

	@GetMapping("/registro")
	public String registro(HttpSession session) {
		
		if (session.getAttribute("usuariologueado") != null) {
			return "redirect:/";
		}
			
		return "registro.html";
	}

	@PostMapping("/registro")
	public String registro(ModelMap modelo, @RequestParam String nombreUsuario, @RequestParam String contrasenia,
			@RequestParam String contrasenia2, @RequestParam String emailUsuario, @RequestParam String nombre,
			@RequestParam String apellido, @RequestParam(required = false) Integer telefono) {

		try {
			usuarioServ.crearUsuario(nombreUsuario, contrasenia, contrasenia2, emailUsuario, nombre, apellido,
					telefono);
			return "redirect:/login";
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

	@PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
	@GetMapping("/modificar")
	public String modificarUsuario(ModelMap modelo) {
		try {
			Usuario usuario = usuarioServ.buscarUsuario(1);
			modelo.addAttribute("usuario", usuario);
		} catch (Exception e) {
			modelo.put("error", e.getMessage());
		}
		return "modificacion_back.html";
	}

	@PostMapping("/modificar")
	public String modificarUsuario(ModelMap modelo, @RequestParam String nombreUsuario,
			@RequestParam String emailUsuario, @RequestParam String nombre, @RequestParam String apellido,
			@RequestParam(required = false) Integer telefono, @RequestParam Integer idUsuario) throws Exception {

		try {
			usuarioServ.modificarUsuario(nombreUsuario, emailUsuario, nombre, apellido, telefono, idUsuario);
			return "redirect:/usuarios/modificar";
		} catch (Exception e) {
			Usuario usuario = usuarioServ.buscarUsuario(idUsuario);
			modelo.addAttribute("usuario", usuario);
			modelo.put("error", e.getMessage());
			return "modificacion_back";
		}
	}

	@PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
	@GetMapping("/inscripcion")
	public String inscripcion() {
		return "inscripcion_back.html";
	}

	@PostMapping("/inscripcion")
	public String inscribir(@RequestParam Integer idUsuario, @RequestParam Integer idActividad, ModelMap modelo) {
		try {
			usuarioServ.inscripcionActividad(idUsuario, idActividad);

		} catch (Exception e) {
			modelo.put("error", e.getMessage());
			return "inscripcion_back.html";
		}

		return "redirect:/usuarios/inscripcion";
	}

	@PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
	@GetMapping("/desinscripcion")
	public String desinscripcion() {
		return "desinscripcion_back.html";
	}

	@PostMapping("/desinscripcion")
	public String desinscribir(@RequestParam Integer idUsuario, @RequestParam Integer idActividad, ModelMap modelo) {
		try {
			usuarioServ.desinscripcionActividad(idUsuario, idActividad);

		} catch (Exception e) {
			modelo.put("error", e.getMessage());
			return "desinscripcion_back.html";
		}

		return "redirect:/usuarios/desinscripcion";
	}

	@PreAuthorize("hasAnyRole('ROLE_USUARIO_ADMIN')")
	@GetMapping("/bajaUsuario")
	public String bajaUsuario() {
		return "bajaUsuario_back.html";
	}

	@PostMapping("/bajaUsuario")
	public String bajarUsuario(@RequestParam Integer id, ModelMap modelo) {
		try {
			usuarioServ.bajaUsuario(id);
		} catch (Exception e) {
			modelo.put("error", e.getMessage());
		}
		return "bajaUsuario_back.html";
	}
}
