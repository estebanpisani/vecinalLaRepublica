package com.grupo9.vecinal.Controladores;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.grupo9.vecinal.Entidades.Actividad;
import com.grupo9.vecinal.Servicios.ActividadServicio;


@Controller
@RequestMapping("/actividades")
public class ActividadControlador {

	@Autowired
	private ActividadServicio actividadServ;
	
	@GetMapping("/mostrar")
	public String mostrarActividades(HttpSession session,ModelMap modelo) throws Exception {
		try {
			List<Actividad> actividades = actividadServ.mostrarActividadFechaReciente();
			modelo.addAttribute("actividades", actividades);	
		} catch (Exception e) {			
			modelo.put("error", e.getMessage());
		}
		return "actividad.html";
	}

	
	
	
}
