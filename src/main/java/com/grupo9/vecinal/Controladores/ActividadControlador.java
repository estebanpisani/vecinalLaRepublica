package com.grupo9.vecinal.Controladores;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.text.SimpleDateFormat;  


import com.grupo9.vecinal.Servicios.ActividadServicio;


@Controller
@RequestMapping("/actividades")
public class ActividadControlador {

	@Autowired
	private ActividadServicio actividadServ;

	@GetMapping("/registro-actividad")
	public String registroActividad() {
		return "registroact_back.html";
	}

	@PostMapping("/registro-actividad")
	public String registroActividad(ModelMap modelo, @RequestParam String nombreActividad, @RequestParam String descripcion, @RequestParam String fecha) throws ParseException {


		try {
			actividadServ.crearActividad(nombreActividad, descripcion, fecha, 5);

			return "redirect:/";
		} catch (Exception e) {
			modelo.put("error", e.getMessage());
			modelo.put("nombreActividad", nombreActividad);
			modelo.put("descripcion", descripcion);
			//modelo.put("fecha", fecha);
			//modelo.put("cupo", cupo);

			return "registroact_back.html";
		}

	}
	
	/*

	@GetMapping("/modificar-actividad")
	public String modificarActividad(ModelMap modelo) {
		try {
			Actividad actividad = actividadServ.buscarActividad(1);
			modelo.addAttribute("actividad", actividad);
		} catch (Exception e) {
			modelo.put("error", e.getMessage());
		}
		return "modificacion-act_back.html";
	}

	@PostMapping("/modificar-actividad")
	public String modificarActividad(ModelMap modelo, @RequestParam String nombreUsuario,
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
*/

}
