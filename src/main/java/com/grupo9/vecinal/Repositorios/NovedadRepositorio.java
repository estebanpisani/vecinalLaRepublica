package com.grupo9.vecinal.Repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.grupo9.vecinal.Entidades.Novedad;

@Repository
public interface NovedadRepositorio extends JpaRepository<Novedad, Integer> {

	@Query("SELECT n FROM Novedad n WHERE n.alta = true ORDER BY n.fecha ASC")
	public List<Novedad> novedadesAlta();
	
	@Query("SELECT n FROM Novedad n WHERE n.alta = false ORDER BY n.fecha ASC")
	public List<Novedad> novedadesBaja();
}
