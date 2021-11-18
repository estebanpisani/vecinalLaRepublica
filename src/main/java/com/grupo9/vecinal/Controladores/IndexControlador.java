package com.grupo9.vecinal.Controladores;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.grupo9.vecinal.Entidades.Actividad;
import com.grupo9.vecinal.Entidades.Novedad;
import com.grupo9.vecinal.Entidades.Usuario;
import com.grupo9.vecinal.Servicios.ActividadServicio;
import com.grupo9.vecinal.Servicios.MailServicio;
import com.grupo9.vecinal.Servicios.NovedadServicio;

@Controller
@RequestMapping("/")
public class IndexControlador {

	@Autowired
	NovedadServicio novedadServ;
	@Autowired
	ActividadServicio actividadServ;

	@Autowired
	MailServicio enviarMails;

	@GetMapping("/")
	public String index(ModelMap modelo, HttpSession session) {
		try {
			List<Novedad> novedadesDestacadas = novedadServ.mostrarNovedadesDestacadas();
			if (novedadesDestacadas.size() > 3) {
				novedadesDestacadas = novedadServ.mostrarNovedadesDestacadas().subList(0, 3);
			}
			modelo.addAttribute("novedades", novedadesDestacadas);
			List<Actividad> actividades = actividadServ.mostrarActividadAlta();
			if (actividades.size() > 3) {
				actividades = actividadServ.mostrarActividadAlta().subList(0, 3);
			}
			modelo.addAttribute("actividades", actividades);
		} catch (Exception e) {

		}
		return "index.html";
	}

	@GetMapping("/login")
	public String login(HttpSession session, ModelMap modelo, @RequestParam(required = false) String error,
			@RequestParam(required = false) String baja, @RequestParam(required = false) String logout) {
		Usuario usuario = (Usuario) session.getAttribute("usuariologueado");
		if (error != null) {
			modelo.put("error", "Usuario o contraseña incorrectas");
			session.setAttribute("usuariologueado", null);

		}
		if (logout != null) {
			modelo.put("ok", "Gracias por su visita.");
		}
		if (baja != null) {
			modelo.put("error", "Usuario dado de baja, se envió un correo para dar de alta");
			try {
				enviarMails.sendVerificacionEmail(usuario);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
			session.setAttribute("usuariologueado", null);

		}
		if (session.getAttribute("usuariologueado") != null) {
			return "redirect:/";
		}

		return "login.html";

	}

	@GetMapping("/default")
	public String roles(HttpSession session) {
		Usuario usuario = (Usuario) session.getAttribute("usuariologueado");
		if (usuario == null) {
			return "redirect:/";
		}
		if (usuario.getAlta() == false) {
			return "redirect:/login?baja";
		}
		if (usuario.getAdmin()) {
			return "redirect:/admin/bajaUsuario";
		} else {
			return "redirect:/usuarios/inscripcion";
		}

	}

}
