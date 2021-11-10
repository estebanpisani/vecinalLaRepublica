package com.grupo9.vecinal.Repositorios;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.grupo9.vecinal.Entidades.Actividad;

@Repository
public interface ActividadRepositorio  extends JpaRepository<Actividad, Integer> {

	@Query("SELECT a FROM Actividad a WHERE a.alta = true ORDER BY a.nombreActividad ASC")
	public List<Actividad> actividadAlta();
	
	@Query("SELECT a FROM Actividad a WHERE a.alta = true ORDER BY a.nombreActividad ASC")
	public List<Actividad> actividadBaja();
	
	@Query("SELECT a FROM Actividad a WHERE a.nombreActividad LIKE :nombre ORDER BY a.nombreActividad ASC")
	public List<Actividad> actividadNombre(@Param("nombre") String nombre);

	
}

