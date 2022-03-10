package com.grupo9.vecinal.Servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.grupo9.vecinal.Entidades.Foto;
import com.grupo9.vecinal.Entidades.Institucion;
import com.grupo9.vecinal.Repositorios.InstitucionRepositorio;

@Service
public class InstitucionServicio {
	
	@Autowired
	InstitucionRepositorio institucionRepo;
	
	@Autowired
	FotoServicio fotoServ;

	@Transactional
	public void crearInstitucion(MultipartFile archivo, String nombre, String descripcion, String direccion, Long telefono) throws Exception {

		try {
			
			validarDatosInstitucion("crear", nombre, descripcion, direccion, telefono);
			
			Institucion institucion = new Institucion();
			
			institucion.setNombre(nombre);
			institucion.setDescripcion(descripcion);
			institucion.setDireccion(direccion);
			institucion.setAlta(true);
			institucion.setTelefono(telefono);
			
			Foto foto = fotoServ.guardar(archivo);
			institucion.setFoto(foto);
			
			institucionRepo.save(institucion);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}

	}
	
	@Transactional
	public void modificarInstitucion(MultipartFile foto, Integer id, String nombre, String descripcion, String direccion, Long telefono) throws Exception {

		try {
			
			validarDatosInstitucion("modificar", nombre, descripcion, direccion, telefono);
			
			Optional<Institucion> respuesta = institucionRepo.findById(id);
			
			if (respuesta.isPresent()) {
				
				Institucion institucion = respuesta.get();
				
				institucion.setNombre(nombre);
				institucion.setDescripcion(descripcion);
				institucion.setDireccion(direccion);
				institucion.setTelefono(telefono);
				if (!foto.isEmpty()) {
					if (institucion.getFoto() != null) {
						institucion.setFoto(fotoServ.actualizar(institucion.getFoto().getId(), foto));
					} else {
						institucion.setFoto(fotoServ.guardar(foto));
					}
				}
				
				institucionRepo.save(institucion);
				
			}else {
				
				throw new Exception("No se encuentra la institucion");
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}

	}
	
	@Transactional
	public void bajaInstitucion(Integer id) throws Exception {

		try {
			Optional<Institucion> respuesta = institucionRepo.findById(id);
			if (respuesta.isPresent()) {
				
				Institucion institucion = respuesta.get();
				institucion.setAlta(false);
				institucionRepo.save(institucion);
				
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
			Optional<Institucion> respuesta = institucionRepo.findById(id);
			if (respuesta.isPresent()) {
				
				Institucion institucion = respuesta.get();
				institucion.setAlta(true);
				institucionRepo.save(institucion);
				
			} else {
				
				throw new Exception("No se encuentra la institucion");
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}

	}
	
	
	private void validarDatosInstitucion(String accion, String nombre, String descripcion, String direccion, Long telefono) throws Exception {

		if (nombre == null || nombre.isEmpty())

		{
			throw new Exception("Error al " + accion + " institucion: El campo 'Nombre' no es valido.");

		}
		if (descripcion == null || descripcion.isEmpty())

		{
			throw new Exception("Error al " + accion + " institucion: El campo 'Descripcion' no es valido.");

		}
		if (direccion == null || direccion.isEmpty())

		{
			throw new Exception("Error al " + accion + " institucion: El campo 'Direccion' no es valido.");

		}
		if (telefono == null || telefono < 100000)

		{
			throw new Exception("Error al " + accion + " institucion: El campo 'Telefono' no es valido.");

		}
	}
	
	@Transactional(readOnly = true)
	public Institucion mostrarInstitucion(Integer id) throws Exception {
		Institucion institucion = institucionRepo.findById(id).get();
		if (institucion == null) {
			throw new Exception("No hay institucion con ese Id");
		} else {
			return institucion;
		}
	}
	
	@Transactional(readOnly = true)
	public List<Institucion> mostrarInstitucionesAlta() throws Exception {
		List<Institucion> instituciones = institucionRepo.institucionesAlta();
		if (instituciones.isEmpty()) {
			throw new Exception("No hay institucion activas");
		} else {
			return instituciones;
		}
	}
	
	@Transactional(readOnly = true)
	public List<Institucion> mostrarInstituciones() throws Exception {
		try {
		return institucionRepo.findAll();
		
		}catch (Exception e) {
			throw new Exception("No se encontraron instituciones guardadas");
		} 
	}
	
	@Transactional(readOnly = true)
	public Institucion buscarInstitucion(Integer id) throws Exception {
		try {
			Optional<Institucion> respuesta = institucionRepo.findById(id);

			if (respuesta.isPresent()) {
				Institucion institucion = respuesta.get();
				return institucion;
			} else {
				throw new Exception("Institucion no encontrada");
			}

		} catch (Exception e) {
			throw new Exception("Institucion no encontrada");
		}

	}

}
