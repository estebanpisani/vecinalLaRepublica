package com.grupo9.vecinal.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.grupo9.vecinal.Servicios.ActividadServicio;
import com.grupo9.vecinal.Servicios.ComercioServicio;
import com.grupo9.vecinal.Servicios.InstitucionServicio;
import com.grupo9.vecinal.Servicios.NovedadServicio;
import com.grupo9.vecinal.Servicios.UsuarioServicio;

@Controller
@PreAuthorize("hasAnyRole('ROLE_USUARIO_ADMIN')")
@RequestMapping("/admin")
public class AdminControlador {
	
	@Autowired
	UsuarioServicio usuarioServ;

	@Autowired
	NovedadServicio novedadServ;
	
	@Autowired
	ActividadServicio actividadServ;
	
	@Autowired
	ComercioServicio comercioServ;
	
	@Autowired
	InstitucionServicio institucionServ;
	
	@GetMapping("/bajaUsuario")
	public String bajaUsuario() {
		return "bajaUsuario_back.html";
	}

	@PostMapping("/bajaUsuario")
	public String bajarUsuario(@RequestParam Integer id, ModelMap modelo) {
		try {
			usuarioServ.bajaUsuario(id);
		} catch (Exception e) {
			modelo.put("error", e.getMessage());
		}
		return "bajaUsuario_back.html";
	}
	
	@GetMapping("/crear-novedades")
	public String crearNovedad(ModelMap modelo) {
		return "crearNovedades.html";
	}

	@PostMapping("/crear-novedades")
	public String crearNovedades(ModelMap modelo, @RequestParam(required = false) MultipartFile foto,
			@RequestParam String titulo, @RequestParam String descripcion,
			@RequestParam(required = false) Boolean destacado) {

		novedadServ.crearNovedad(foto, titulo, descripcion, destacado);

		return "redirect:/admin/crear";
	}

}
