package com.grupo9.vecinal.Repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.grupo9.vecinal.Entidades.Comercio;

@Repository
public interface ComercioRepositorio extends JpaRepository<Comercio, Integer> {
	
	
	@Query("SELECT n FROM Comercio n ORDER BY n.nombre ASC")
	public List<Comercio> comercios();
	
	@Query("SELECT n FROM Comercio n WHERE n.alta = true ORDER BY n.nombre ASC")
	public List<Comercio> comerciosAlta();

}
