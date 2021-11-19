package com.grupo9.vecinal.Controladores;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.grupo9.vecinal.Entidades.Actividad;
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
	
	@GetMapping("/registro-actividad")
	public String registroActividad() {
		return "panel-actividades.html";
	}

	@PostMapping("/registro-actividad")
	public String registroActividad(ModelMap modelo, @RequestParam String nombreActividad, @RequestParam String descripcion, @RequestParam String fecha, @RequestParam Integer cupo) throws ParseException {


		try {
			actividadServ.crearActividad(nombreActividad, descripcion, fecha, cupo);

			return "redirect:/";
		} catch (Exception e) {
			modelo.put("error", e.getMessage());
			modelo.put("nombreActividad", nombreActividad);
			modelo.put("descripcion", descripcion);
			modelo.put("cupo", cupo);

			return "panel-actividades.html";
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
			return "redirect:/admin/registro-actividad";
		} catch (Exception e) {
			Actividad actividad = actividadServ.buscarActividad(id);
			modelo.addAttribute("actividad", actividad);
			modelo.put("error", e.getMessage());
			return "modificacion_back";
		}
	}
	
	@PostMapping("/baja-actividad")
	public String bajaActividad(@RequestParam Integer id) throws Exception {	
			actividadServ.bajaActividad(id);
			return "redirect:/admin/registro-actividad";
	}	

	@PostMapping("/registrar-comercio")
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
			
			return "redirect:/admin/registrar-comercio";
		}

	}
	
	@PostMapping("/modificar-comercio")
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
			
			return "redirect:/admin/modificar-comercio";
		}

	}

	@GetMapping("/registrar-institucion")
	public String registrarInstitucion(ModelMap modelo) {
		//List<Institucion> instituciones = institucionRepo.findAll();
		//modelo.put("instituciones", instituciones);
		return "crearinstituciones.html";
	}
	
	@PostMapping("/registrar-institucion")
	public String registrarInstitucion(ModelMap modelo, MultipartFile archivo, @RequestParam String nombre, @RequestParam String descripcion,
			@RequestParam String direccion, @RequestParam Long telefono, @RequestParam(required = false) String contrasenia) {
		try {
			institucionServ.crearInstitucion(archivo, nombre, descripcion, direccion, telefono);
			return "redirect:/instituciones/mostrar";
			
		} catch (Exception e) {
			e.printStackTrace();
			modelo.put("error", e.getMessage());
			modelo.put("nombre", nombre);
			modelo.put("descripcion", descripcion);
			modelo.put("direccion", direccion);
			modelo.put("telefono", telefono);
			
			return "redirect:/admin/registrar-institucion";
		}

	}
	
	@PostMapping("/modificar-institucion")
	public String modificarInstitucion(ModelMap modelo, MultipartFile archivo, @RequestParam Integer id, @RequestParam String nombre, @RequestParam String descripcion,
			@RequestParam String direccion, @RequestParam Long telefono) {
		
		try {
			institucionServ.modificarInstitucion(archivo, id, nombre, descripcion, direccion, telefono);
			return "instituciones.html";
			
		} catch (Exception e) {
			e.printStackTrace();
			modelo.put("error", e.getMessage());
			modelo.put("nombre", nombre);
			modelo.put("descripcion", descripcion);
			modelo.put("direccion", direccion);
			modelo.put("telefono", telefono);
			
			return "redirect:/admin/modificar-institucion";
		}

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

		return "redirect:/admin/crear-novedades";
	}

}
