package com.grupo9.vecinal.Servicios;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grupo9.vecinal.Entidades.Actividad;
import com.grupo9.vecinal.Repositorios.ActividadRepositorio;

@Service
public class ActividadServicio {
	
	@Autowired
	private ActividadRepositorio actividadRepo;
	
	@Transactional	
	public void validarActividad(String nombreActividad, String descripcionActividad, Date fecha, Integer cupo)
			throws Exception {

		if (nombreActividad == null || nombreActividad.isEmpty())

		{
			throw new Exception("El campo no puede estar vacio.");

		}

		if (descripcionActividad == null || descripcionActividad.isEmpty())

		{
			throw new Exception("El campo no puede estar vacio.");

		}
		if (fecha == null || ((CharSequence) fecha).isEmpty())

		{
			throw new Exception("El campo no puede estar vacio.");

		}
		if (cupo == null)

		{
			throw new Exception("El campo no puede estar vacio.");

		}

	}
	
	@Transactional
	public void crearActividad(String nombreActividad, String descripcionActividad, Date fecha, Integer cupo) throws Exception {
		try {
			validarActividad(nombreActividad, descripcionActividad, fecha, cupo);

			Actividad actividad = new Actividad();
			actividad.setNombreActividad(nombreActividad);
			actividad.setDescripcionActividad(descripcionActividad);
			actividad.setFecha(fecha);
			actividad.setCupo(cupo);
			actividad.setAlta(true);

			actividadRepo.save(actividad);

		} catch (Exception e) {
			e.getMessage();
			throw new Exception("Error en uno de los campos");
		}

	}
	
	@Transactional
	public void modificarActividad(String nombreActividad, String descripcionActividad, Date fecha, Integer cupo, Integer id) throws Exception {
		try {
			validarActividad(nombreActividad, descripcionActividad, fecha, cupo);
			Optional<Actividad> respuesta = actividadRepo.findById(id);

			if (respuesta.isPresent()) {
				Actividad actividad = respuesta.get();
				actividad.setNombreActividad(nombreActividad);
				actividad.setDescripcionActividad(descripcionActividad);
				actividad.setFecha(fecha);
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
	
	
	

}
