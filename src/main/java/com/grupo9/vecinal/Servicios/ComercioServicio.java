package com.grupo9.vecinal.Servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grupo9.vecinal.Entidades.Actividad;
import com.grupo9.vecinal.Entidades.Comercio;
import com.grupo9.vecinal.Repositorios.ComercioRepositorio;

@Service
public class ComercioServicio {
	
	@Autowired
	ComercioRepositorio comercioRepo;

	@Transactional
	public void crearComercio(String nombre, String descripcion, String direccion, Long telefono) throws Exception {

		try {
			
			validarDatosComercio("crear", nombre, descripcion, direccion, telefono);
			
			Comercio comercio = new Comercio();
			
			comercio.setNombre(nombre);
			comercio.setDescripcion(descripcion);
			comercio.setDireccion(direccion);
			comercio.setAlta(true);
			comercio.setTelefono(telefono);
			
			comercioRepo.save(comercio);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}

	}
	
	@Transactional
	public void modificarComercio(Integer id, String nombre, String descripcion, String direccion, Long telefono) throws Exception {

		try {
			
			validarDatosComercio("modificar", nombre, descripcion, direccion, telefono);
			
			Optional<Comercio> respuesta = comercioRepo.findById(id);
			
			if (respuesta.isPresent()) {
				
				Comercio comercio = respuesta.get();
				
				comercio.setNombre(nombre);
				comercio.setDescripcion(descripcion);
				comercio.setDireccion(direccion);
				comercio.setTelefono(telefono);
				
				comercioRepo.save(comercio);
				
			}else {
				
				throw new Exception("No se encuentra el comercio");
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}

	}
	
	@Transactional
	public void bajaComercio(Integer id) throws Exception {

		try {
			Optional<Comercio> respuesta = comercioRepo.findById(id);
			if (respuesta.isPresent()) {
				
				Comercio institucion = respuesta.get();
				institucion.setAlta(false);
				comercioRepo.save(institucion);
				
			} else {
				
				throw new Exception("No se encuentra la institucion");
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}

	}
	
	@Transactional
	public void altaInstitucion(Integer id) throws Exception {

		try {
			Optional<Comercio> respuesta = comercioRepo.findById(id);
			if (respuesta.isPresent()) {
				
				Comercio institucion = respuesta.get();
				institucion.setAlta(true);
				comercioRepo.save(institucion);
				
			} else {
				
				throw new Exception("No se encuentra el comercio");
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}

	}
	
	@Transactional(readOnly = true)
	public List<Comercio> mostrarComercios() throws Exception {
		try {
		return comercioRepo.findAll();
		
		}catch (Exception e) {
			throw new Exception("No se encontraron comercios guardados");
		} 
	}
	
	
	@Transactional(readOnly = true)
	public Comercio buscarComercio(Integer id) throws Exception {
		try {
			Optional<Comercio> respuesta = comercioRepo.findById(id);

			if (respuesta.isPresent()) {
				Comercio comercio = respuesta.get();
				return comercio;
			} else {
				throw new Exception("Comercio no encontrado");
			}

		} catch (Exception e) {
			throw new Exception("Comercio no encontrado");
		}

	}
	
	private void validarDatosComercio(String accion, String nombre, String descripcion, String direccion, Long telefono) throws Exception {

		if (nombre == null || nombre.isEmpty())

		{
			throw new Exception("Error al " + accion + " comercio: El campo 'Nombre' no es valido.");

		}
		if (descripcion == null || descripcion.isEmpty())

		{
			throw new Exception("Error al " + accion + " comercio: El campo 'Descripcion' no es valido.");

		}
		if (direccion == null || direccion.isEmpty())

		{
			throw new Exception("Error al " + accion + " comercio: El campo 'Direccion' no es valido.");

		}
		if (telefono == null || telefono < 100000)

		{
			throw new Exception("Error al " + accion + " comercio: El campo 'Telefono' no es valido.");

		}
	}

}
