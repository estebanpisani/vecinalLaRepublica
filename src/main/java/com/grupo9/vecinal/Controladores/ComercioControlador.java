package com.grupo9.vecinal.Controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.grupo9.vecinal.Entidades.Comercio;
import com.grupo9.vecinal.Repositorios.ComercioRepositorio;
import com.grupo9.vecinal.Servicios.ComercioServicio;

@Controller
@RequestMapping("/comercios")
public class ComercioControlador {
	
	@Autowired
	private ComercioRepositorio comercioRepo;
	
	@Autowired
	private ComercioServicio comercioServ;
	
	@GetMapping("/mostrar")
	public String mostrarComercios(ModelMap modelo) {
		List<Comercio> comercios = comercioRepo.findAll();
		modelo.put("comercios", comercios);
		return "comercios.html";
	}
	
	@PostMapping("/registrar")
	public String registrarComercio(ModelMap modelo, @RequestParam String nombre, @RequestParam String descripcion,
			@RequestParam String direccion, @RequestParam Long telefono) {
		try {
			comercioServ.crearComercio(nombre, descripcion, direccion, telefono);
			return "comercios.html";
			
		} catch (Exception e) {
			e.printStackTrace();
			modelo.put("error", e.getMessage());
			modelo.put("nombre", nombre);
			modelo.put("descripcion", descripcion);
			modelo.put("direccion", direccion);
			modelo.put("telefono", telefono);
			
			return "redirect:/comercios/registrar";
		}

	}
	
	@PostMapping("/modificar")
	public String modificarComercio(ModelMap modelo, @RequestParam Integer id, @RequestParam String nombre, @RequestParam String descripcion,
			@RequestParam String direccion, @RequestParam Long telefono) {
		
		try {
			comercioServ.modificarComercio(id, nombre, descripcion, direccion, telefono);
			return "comercios.html";
			
		} catch (Exception e) {
			e.printStackTrace();
			modelo.put("error", e.getMessage());
			modelo.put("nombre", nombre);
			modelo.put("descripcion", descripcion);
			modelo.put("direccion", direccion);
			modelo.put("telefono", telefono);
			
			return "redirect:/comercios/modificar";
		}

	}

}
