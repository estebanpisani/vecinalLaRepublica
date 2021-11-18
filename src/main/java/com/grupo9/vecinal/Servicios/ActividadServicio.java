package com.grupo9.vecinal.Servicios;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grupo9.vecinal.Entidades.Actividad;
import com.grupo9.vecinal.Entidades.Usuario;
import com.grupo9.vecinal.Repositorios.ActividadRepositorio;

@Service
public class ActividadServicio {
	
	@Autowired
	private ActividadRepositorio actividadRepo;
	
	@Transactional	
	public void validarActividad(String nombreActividad, String descripcionActividad, Integer cupo)
			throws Exception {

		if (nombreActividad == null || nombreActividad.isEmpty())

		{
			throw new Exception("El campo no puede estar vacio.");

		}

		if (descripcionActividad == null || descripcionActividad.isEmpty())

		{
			throw new Exception("El campo no puede estar vacio.");

		}
		
		if (cupo == null)

		{
			throw new Exception("El campo no puede estar vacio.");

		}

	}
	
	@Transactional
	public void crearActividad(String nombreActividad, String descripcionActividad, String fecha, Integer cupo) throws Exception {

		try {
			validarActividad(nombreActividad, descripcionActividad, cupo);
			
			//SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd");
			//DateFormat formatter = new SimpleDateFormat("dd-mm-YYYY", Locale.ENGLISH);
			//Date date = formatter.parse(fecha);
			//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
			
			LocalDate localDate = LocalDate.parse(fecha);
			
			Actividad actividad = new Actividad();
			actividad.setNombreActividad(nombreActividad);
			actividad.setDescripcionActividad(descripcionActividad);
			actividad.setFecha(localDate);
			actividad.setCupo(cupo);
			actividad.setInscriptos(0);				
			actividad.setAlta(true);

			actividadRepo.save(actividad);

		} catch (Exception e) {
			e.getMessage();
			throw new Exception(e.getMessage());
		}

	}
	
	@Transactional
	public void modificarActividad(String nombreActividad, String descripcionActividad, String fecha, Integer cupo, Integer id) throws Exception {
		try {
			validarActividad(nombreActividad, descripcionActividad, cupo);
			
			LocalDate localDate = LocalDate.parse(fecha);
			
			
			Optional<Actividad> respuesta = actividadRepo.findById(id);

			if (respuesta.isPresent()) {
				Actividad actividad = respuesta.get();
				actividad.setNombreActividad(nombreActividad);
				actividad.setDescripcionActividad(descripcionActividad);
				actividad.setFecha(localDate);
				actividad.setCupo(cupo);

				actividadRepo.save(actividad);

			} else {
				throw new Exception("Actividad no encontrada");
			}

		} catch (Exception e) {
			e.getMessage();
		}
	}

	
	
	@Transactional
	public void bajaActividad(Integer id) throws Exception {
		try {
			Optional<Actividad> respuesta = actividadRepo.findById(id);

			if (respuesta.isPresent()) {
				Actividad actividad = respuesta.get();
				actividad.setAlta(false);

				actividadRepo.save(actividad);

			} else {
				throw new Exception("Actividad no encontrada");
			}

		} catch (Exception e) {
			e.getMessage();
		}

	}
	
	@Transactional(readOnly = true)
	public List<Actividad> mostrarActividades() throws Exception {
		try {
			return actividadRepo.findAll();

		} catch (Exception e) {
			throw new Exception("No se encontraron actividades.");
		}

	}
	
	
	@Transactional(readOnly = true)
	public Actividad buscarActividad(Integer id) throws Exception {
		try {
			Optional<Actividad> respuesta = actividadRepo.findById(id);

			if (respuesta.isPresent()) {
				Actividad actividad = respuesta.get();
				return actividad;
			} else {
				throw new Exception("Actividad no encontrada");
			}

		} catch (Exception e) {
			throw new Exception("Actividad no encontrada");
		}

	}
	
	@Transactional(readOnly = true)
	public List<Actividad> mostrarActividadAlta() throws Exception {
		try {
			return actividadRepo.actividadAlta();

		} catch (Exception e) {
			throw new Exception("No se encontraron actividades dadas de alta.");
		}

	}
	
	@Transactional(readOnly = true)
	public List<Actividad> mostrarActividadBaja() throws Exception {
		try {
			return actividadRepo.actividadBaja();

		} catch (Exception e) {
			throw new Exception("No se encontraron actividades dadas de baja.");
		}

	}
	
	@Transactional(readOnly = true)
	public List<Actividad> mostrarActividadNombre(String nombre) throws Exception {
		
			List<Actividad> actividades = actividadRepo.actividadNombre("%"+nombre+"%");
			if (actividades.isEmpty()) {
				throw new Exception("No se encontraron actividades con ese título");
			} else {
				return actividades;
			}
	}	
	
	@Transactional(readOnly = true)
	public List<Actividad> mostrarActividadFechaReciente() throws Exception {
		
			List<Actividad> actividades = actividadRepo.actividadesFechaReciente();
			if (actividades.isEmpty()) {
				throw new Exception("No se encontraron actividades.");
			} else {
				return actividades;
			}
	}		
	
	@Transactional(readOnly = true)
	public List<Actividad> mostrarActividadFechaAntigua() throws Exception {
		
			List<Actividad> actividades = actividadRepo.actividadesFechaAntigua();
			if (actividades.isEmpty()) {
				throw new Exception("No se encontraron actividades.");
			} else {
				return actividades;
			}
	}	
	
	
	
	public Set<Usuario> usuariosAnotados(Integer id) throws Exception{
		 
		try {
			Actividad actividad = buscarActividad(id);
			return actividad.getUsuarios();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
	}
	
	
	

}
