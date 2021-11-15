package com.grupo9.vecinal.Controladores;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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

		return "registro_back.html";
	}

	@PostMapping("/registro")
	public String registro(@RequestParam(required = false) MultipartFile foto, ModelMap modelo, @RequestParam String nombreUsuario,
			@RequestParam String contrasenia, @RequestParam String contrasenia2, @RequestParam String emailUsuario,
			@RequestParam String nombre, @RequestParam String apellido,
			@RequestParam(required = false) Long telefono) {

		try {
			usuarioServ.crearUsuario(foto, nombreUsuario, contrasenia, contrasenia2, emailUsuario, nombre, apellido,
					telefono);
			return "esperar.html";
		} catch (Exception e) {
			modelo.put("error", e.getMessage());
			modelo.put("nombreUsuario", nombreUsuario);
			modelo.put("contrasenia", contrasenia);
			modelo.put("contrasenia2", contrasenia2);
			modelo.put("emailUsuario", emailUsuario);
			modelo.put("nombre", nombre);
			modelo.put("apellido", apellido);
			modelo.put("telefono", telefono);

			return "registro_back.html";
		}
	}
	
	@GetMapping("/verificacion/{code}")
	public String usuarioValidado(@PathVariable("code") String verificacion, ModelMap modelo) {
		try {
			usuarioServ.altaUsuario(verificacion);
			modelo.addAttribute("verificado",verificacion);
		} catch (Exception e) {
			modelo.put("error", e.getMessage());
		}
		return "esperar.html";
	}

	@PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
	@GetMapping("/modificar")
	public String modificarUsuario(HttpSession logueado, ModelMap modelo) {
		try {
			Usuario usuario = (Usuario) logueado.getAttribute("usuariologueado");
			modelo.addAttribute("usuario", usuario);
		} catch (Exception e) {
			modelo.put("error", e.getMessage());
		}
		return "modificacion_back.html";
	}

	@PostMapping("/modificar")
	public String modificarUsuario(MultipartFile foto, ModelMap modelo, @RequestParam String nombreUsuario,
			@RequestParam String emailUsuario, @RequestParam String nombre, @RequestParam String apellido,
			@RequestParam(required = false) Long telefono, @RequestParam Integer idUsuario, HttpSession session) throws Exception {

		try {
			usuarioServ.modificarUsuario(foto, nombreUsuario, emailUsuario, nombre, apellido, telefono, idUsuario);
			session.setAttribute("usuariologueado", usuarioServ.buscarUsuario(idUsuario));
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
	
	@GetMapping("/recuperar")
	public String recuperar(ModelMap modelo) {
		modelo.addAttribute("contrasenia", false);
		return "recuperar_back.html";
	}
	
	@PostMapping("/recuperar")
	public String recuperando(ModelMap modelo,@RequestParam String mail, @RequestParam(required = false) String contrasenia, @RequestParam(required = false) String contrasenia2) {
		try {
			if (usuarioServ.recuperarUsuarioOContrasenia(mail, contrasenia, contrasenia2)) {
				return "redirect:/login";
			}else {
				return "recuperar_back.html";
			}
		} catch (Exception e) {
			modelo.put("error", e.getMessage());
			return "recuperar_back.html";
		}
		
		
	}
	
	@GetMapping("/recuperar/{codigo}")
	public String recuperando(@PathVariable String codigo,ModelMap modelo) {
		try {
			Usuario usuario = usuarioServ.buscarUsuarioCodValidacion(codigo);
			if (usuario != null) {
				modelo.addAttribute("contrasenia", true);
				modelo.addAttribute("mail", usuario.getEmailUsuario());
			}
		} catch (Exception e) {
			modelo.put("error", e.getMessage());
		}
		
		return "recuperar_back.html";
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
