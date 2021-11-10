package com.grupo9.vecinal.Repositorios;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.grupo9.vecinal.Entidades.Novedad;

@Repository
public interface NovedadRepositorio extends JpaRepository<Novedad, Integer> {

	@Query("SELECT n FROM Novedad n WHERE n.alta = true ORDER BY n.fecha DESC")
	public List<Novedad> novedadesAlta();
	
	@Query("SELECT n FROM Novedad n WHERE n.alta = false ORDER BY n.fecha DESC")
	public List<Novedad> novedadesBaja();
	
	@Query("SELECT n FROM Novedad n WHERE n.fecha = fecha ORDER BY n.fecha DESC")
	public List<Novedad> novedadesPorFechaNueva(@Param("fecha") Date fecha);
	
	@Query("SELECT n FROM Novedad n WHERE n.fecha = fecha ORDER BY n.fecha ASC")
	public List<Novedad> novedadesPorFechaVieja(@Param("fecha") Date fecha);
	
	@Query("SELECT n FROM Novedad n WHERE n.titulo LIKE :titulo ORDER BY n.fecha DESC")
	public List<Novedad> novedadesPorTitulo(@Param("titulo") String titulo);
	
	@Query("SELECT n FROM Novedad n WHERE n.descripcion LIKE :descripcion ORDER BY n.fecha DESC")
	public List<Novedad> novedadesPorDescripcion(@Param("descripcion") String descripcion);
	
	@Query("SELECT n FROM Novedad n WHERE n.destacado = true ORDER BY n.fecha DESC")
	public List<Novedad> novedadesPorDestacado(@Param("destacado") Boolean destacado);
}
