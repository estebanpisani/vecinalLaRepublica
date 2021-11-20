package com.grupo9.vecinal.Controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.grupo9.vecinal.Entidades.Novedad;
import com.grupo9.vecinal.Servicios.NovedadServicio;

@Controller
@RequestMapping("/novedades")
public class NovedadControlador {

	@Autowired
	public NovedadServicio novedadServ;

	@GetMapping("/mostrar")
	public String mostrarNovedades(ModelMap modelo) {

		try {
			List<Novedad> novedadesDestacadas = novedadServ.mostrarNovedadesDestacadas();
			modelo.addAttribute("novedadesDestacadas", novedadesDestacadas);
			List<Novedad> novedadesNoDestacadas = novedadServ.mostrarNovedadesNoDestacadas();
			modelo.addAttribute("novedadesNoDestacadas", novedadesNoDestacadas);
			modelo.put("novedades", true);
		} catch (Exception e) {
			modelo.put("error", e.getMessage());
		}

		return "novedades.html";
	}
	
	@GetMapping("/mostrar/{id}")
	public String mostrarNovedad(ModelMap modelo, @PathVariable("id") Integer id) {

		try {
			Novedad novedad = novedadServ.mostrarNovedad(id);
			modelo.addAttribute("novedad", novedad);
			modelo.put("novedades", false);

		} catch (Exception e) {
			modelo.put("error", e.getMessage());
		}

		return "novedades.html";
	}
	
}
