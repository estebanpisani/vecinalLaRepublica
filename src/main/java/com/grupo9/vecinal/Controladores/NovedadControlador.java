package com.grupo9.vecinal.Controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.grupo9.vecinal.Entidades.Novedad;
import com.grupo9.vecinal.Servicios.NovedadServicio;

@Controller
@RequestMapping("/novedades")
public class NovedadControlador {

	@Autowired
	public NovedadServicio novedadServ;

	@GetMapping("/crear")
	public String crearNovedad(ModelMap modelo) {

		try {
			List<Novedad> novedades = novedadServ.mostrarAltaNovedades();
			modelo.addAttribute("novedades",novedades);

		} catch (Exception e) {
			modelo.put("errorLista", e.getMessage());
		}

		return "crearNovedades.html";
	}

	@PostMapping("/crear")
	public String crearNovedades(ModelMap modelo, @RequestParam String titulo, @RequestParam String descripcion,
			@RequestParam(required = false) Boolean destacado) {

		novedadServ.crearNovedad(titulo, descripcion, destacado);

		return "redirect:/novedades/crear";
	}
}
