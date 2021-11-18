package com.grupo9.vecinal.Controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.grupo9.vecinal.Entidades.Novedad;
import com.grupo9.vecinal.Servicios.NovedadServicio;

@Controller
@RequestMapping("/novedades")
public class NovedadControlador {

	@Autowired
	public NovedadServicio novedadServ;

	@GetMapping("/mostrar")
	public String mostrarNovedad(ModelMap modelo) {

		try {
			List<Novedad> novedades = novedadServ.mostrarAltaNovedades();
			modelo.addAttribute("novedades", novedades);

		} catch (Exception e) {
			modelo.put("errorLista", e.getMessage());
		}

		return "crearNovedades.html";
	}
	
}
