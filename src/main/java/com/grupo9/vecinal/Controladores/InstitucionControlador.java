package com.grupo9.vecinal.Controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.grupo9.vecinal.Entidades.Institucion;
import com.grupo9.vecinal.Repositorios.InstitucionRepositorio;

@Controller
@RequestMapping("/instituciones")
public class InstitucionControlador {
	
	@Autowired
	private InstitucionRepositorio institucionRepo;
	
	@GetMapping("/mostrar")
	public String mostrarInstituciones(ModelMap modelo) {
		List<Institucion> instituciones = institucionRepo.findAll();
		modelo.put("instituciones", instituciones);
		return "instituciones.html";
	}
	


}
