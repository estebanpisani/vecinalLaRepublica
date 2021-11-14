package com.grupo9.vecinal.Controladores;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.grupo9.vecinal.Entidades.Actividad;
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
	public String registroActividad(ModelMap modelo, @RequestParam String nombreActividad, @RequestParam String descripcion, @RequestParam String fecha, @RequestParam Integer cupo) throws ParseException {


		try {
			actividadServ.crearActividad(nombreActividad, descripcion, fecha, 5);

			return "redirect:/";
		} catch (Exception e) {
			modelo.put("error", e.getMessage());
			modelo.put("nombreActividad", nombreActividad);
			modelo.put("descripcion", descripcion);
			modelo.put("cupo", cupo);

			return "registroact_back.html";
		}

	}
	


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
	public String modificarActividad(ModelMap modelo, @RequestParam String nombreActividad, @RequestParam String descripcion, @RequestParam String fecha, @RequestParam Integer cupo, @RequestParam Integer id) throws Exception {

		try {
			
			actividadServ.modificarActividad(nombreActividad, descripcion, fecha, cupo, id);
			return "redirect:/usuarios/modificar";
		} catch (Exception e) {
			Actividad actividad = actividadServ.buscarActividad(id);
			modelo.addAttribute("actividad", actividad);
			modelo.put("error", e.getMessage());
			return "modificacion_back";
		}
	}


}
