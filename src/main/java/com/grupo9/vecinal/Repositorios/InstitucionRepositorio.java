package com.grupo9.vecinal.Repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.grupo9.vecinal.Entidades.Institucion;

@Repository
public interface InstitucionRepositorio extends JpaRepository<Institucion, Integer> {
	
	
	@Query("SELECT n FROM Institucion n ORDER BY n.nombre ASC")
	public List<Institucion> instituciones();
	
	@Query("SELECT n FROM Institucion n WHERE n.alta = true ORDER BY n.nombre ASC")
	public List<Institucion> institucionesAlta();

}
