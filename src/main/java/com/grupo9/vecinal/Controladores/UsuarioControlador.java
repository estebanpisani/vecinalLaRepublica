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

		return "registro.html";
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

			return "registro.html";
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

	@PostMapping("/modificar")
	public String modificarUsuario(MultipartFile foto, ModelMap modelo, @RequestParam String nombreUsuario,
			@RequestParam String emailUsuario, @RequestParam String nombre, @RequestParam String apellido,
			@RequestParam(required = false) Long telefono, @RequestParam Integer idUsuario, HttpSession session) throws Exception {

		try {
			usuarioServ.modificarUsuario(foto, nombreUsuario, emailUsuario, nombre, apellido, telefono, idUsuario);
			session.setAttribute("usuariologueado", usuarioServ.buscarUsuario(idUsuario));
			return "redirect:/usuarios/panel";
		} catch (Exception e) {
			Usuario usuario = usuarioServ.buscarUsuario(idUsuario);
			modelo.addAttribute("usuario", usuario);
			modelo.put("error", e.getMessage());
			return "panel_usuario.html";
		}
	}

	@PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
	@GetMapping("/inscripcion/{idActividad}")
	public String inscripcion(HttpSession session, @PathVariable("idActividad") Integer idActividad, ModelMap modelo){

			try {
				Usuario usuario=(Usuario)session.getAttribute("usuariologueado");
				usuarioServ.inscripcionActividad(usuario.getIdUsuario(), idActividad);
				session.setAttribute("usuariologueado", usuarioServ.buscarUsuario(usuario.getIdUsuario()));

			} catch (Exception e) {
				modelo.put("error", e.getMessage());
				return "redirect:/actividades/mostrar";
			}

			return "redirect:/actividades/mostrar";
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
				modelo.addAttribute("mail",usuario.getEmailUsuario());
				return "recuperar_back.html";
			}
		} catch (Exception e) {
			modelo.put("error", e.getMessage());
		}
		
		return "recuperar_back.html";
	}

	@PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
	@GetMapping("/desinscripcion/{idActividad}")
	public String desinscribir(HttpSession session, @PathVariable("idActividad") Integer idActividad, ModelMap modelo) {
		try {
			Usuario usuario=(Usuario)session.getAttribute("usuariologueado");
			usuarioServ.desinscripcionActividad(usuario.getIdUsuario(), idActividad);
			session.setAttribute("usuariologueado", usuarioServ.buscarUsuario(usuario.getIdUsuario()));

		} catch (Exception e) {
			modelo.put("error", e.getMessage());
			return "panel_actividades_usuario.html";
		}

		return "redirect:/usuarios/panel-actividades";
	}

	

	// Panel
	@PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
	@GetMapping("/panel")
	public String panelUsuario(HttpSession session, ModelMap modelo) {
		Usuario usuario = (Usuario) session.getAttribute("usuariologueado");
		modelo.addAttribute("usuario", usuario);
		
		return "panel_usuario.html";
	}
	
	@PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
	@GetMapping("/panel-actividades")
	public String panelUsuarioActividades(HttpSession session, ModelMap modelo) throws Exception {
		Usuario usuario = (Usuario) session.getAttribute("usuariologueado");
		modelo.addAttribute("usuario", usuario);
		return "panel_actividades_usuario.html";
	}
	
	@PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
	@GetMapping("/panel-cambiarcontrasena")
	public String panelCambiarContrasena() {
		
		return "panel_cambiarcontrasena";
	}
	
	@PostMapping("/panel-cambiarcontrasena")
	public String panelContraseniaCambiada(HttpSession session,ModelMap modelo, @RequestParam Integer id, @RequestParam String contraseniaActual, @RequestParam String contraseniaNueva, @RequestParam String contraseniaNueva2) {
		try {
			usuarioServ.modificarContrasenia(contraseniaActual, contraseniaNueva, contraseniaNueva2, id);
			session.setAttribute("usuariologueado", usuarioServ.buscarUsuario(id));
			modelo.put("ok", "¡Contraseña cambiada con éxito!");
			return "panel_cambiarcontrasena";
			
		} catch (Exception e) {
			modelo.put("error", e.getMessage());
			return "panel_cambiarcontrasena";
		}
		
	}
	
	@PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
	@GetMapping("/eliminar-foto/{idFoto}")
	public String eliminarFoto(HttpSession session, ModelMap modelo,@PathVariable("idFoto") Integer idFoto) {
		try {
			Usuario usuario =(Usuario)session.getAttribute("usuariologueado");
			if (usuario.getFoto()!=null) {
				usuarioServ.eliminarFotoUsuario(usuario.getIdUsuario(),idFoto);
				session.setAttribute("usuariologueado",usuarioServ.buscarUsuario(usuario.getIdUsuario()));
				return "redirect:/usuarios/panel";
			}else {
				modelo.put("error", "No hay foto para eliminar");
				return "panel_usuario.html";
			}
		} catch (Exception e) {
			modelo.put("error", e.getMessage());
			return "panel_usuario.html";
		}
		
		
	}
}
