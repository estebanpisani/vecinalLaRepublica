package com.grupo9.vecinal.Controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.grupo9.vecinal.Entidades.Comercio;
import com.grupo9.vecinal.Repositorios.ComercioRepositorio;

@Controller
@RequestMapping("/comercios")
public class ComercioControlador {
	
	@Autowired
	private ComercioRepositorio comercioRepo;
	
	@GetMapping("/mostrar")
	public String mostrarComercios(ModelMap modelo) {
		List<Comercio> comercios = comercioRepo.findAll();
		modelo.put("comercios", comercios);
		return "comercios.html";
	}
	
	
}
