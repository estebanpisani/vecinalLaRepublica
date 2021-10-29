package com.grupo9.vecinal.Servicios;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grupo9.vecinal.Entidades.Novedad;
import com.grupo9.vecinal.Repositorios.NovedadRepositorio;

@Service
public class NovedadServicio {

	@Autowired
	NovedadRepositorio novedadRepo;

	@Transactional
	public void crearNovedad(String titulo, String descripcion) {
		
		try {
			validarDatosNovedad(titulo, descripcion);
			Novedad novedad =new Novedad();
			novedad.setDescripcion(descripcion);
			novedad.setTitulo(titulo);
			novedad.setAlta(true);
			novedad.setFecha(new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void validarDatosNovedad(String titulo, String descripcion) throws Exception {
		
		if (titulo == null || titulo.isEmpty())

		{
			throw new Exception("El campo no puede estar vacio.");

		}
		if (descripcion == null || descripcion.isEmpty())

		{
			throw new Exception("El campo no puede estar vacio.");

		}
	}

}
