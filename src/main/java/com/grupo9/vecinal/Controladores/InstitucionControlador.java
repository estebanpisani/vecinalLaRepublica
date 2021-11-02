package com.grupo9.vecinal.Controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.grupo9.vecinal.Entidades.Institucion;
import com.grupo9.vecinal.Repositorios.InstitucionRepositorio;
import com.grupo9.vecinal.Servicios.InstitucionServicio;

@Controller
@RequestMapping("/instituciones")
public class InstitucionControlador {
	
	@Autowired
	private InstitucionRepositorio institucionRepo;
	
	@Autowired
	private InstitucionServicio institucionServ;
	
	@GetMapping("/mostrar")
	public String mostrarInstituciones(ModelMap modelo) {
		List<Institucion> instituciones = institucionRepo.findAll();
		modelo.put("instituciones", instituciones);
		return "instituciones.html";
	}
	
	@PostMapping("/registrar")
	public String registrarInstitucion(ModelMap modelo, @RequestParam String nombre, @RequestParam String descripcion,
			@RequestParam String direccion, @RequestParam Long telefono) {
		try {
			institucionServ.crearInstitucion(nombre, descripcion, direccion, telefono);
			return "instituciones.html";
			
		} catch (Exception e) {
			e.printStackTrace();
			modelo.put("error", e.getMessage());
			modelo.put("nombre", nombre);
			modelo.put("descripcion", descripcion);
			modelo.put("direccion", direccion);
			modelo.put("telefono", telefono);
			
			return "redirect:/instituciones/registrar";
		}

	}
	
	@PostMapping("/modificar")
	public String modificarInstitucion(ModelMap modelo, @RequestParam Integer id, @RequestParam String nombre, @RequestParam String descripcion,
			@RequestParam String direccion, @RequestParam Long telefono) {
		
		try {
			institucionServ.modificarInstitucion(id, nombre, descripcion, direccion, telefono);
			return "instituciones.html";
			
		} catch (Exception e) {
			e.printStackTrace();
			modelo.put("error", e.getMessage());
			modelo.put("nombre", nombre);
			modelo.put("descripcion", descripcion);
			modelo.put("direccion", direccion);
			modelo.put("telefono", telefono);
			
			return "redirect:/instituciones/modificar";
		}

	}

}
