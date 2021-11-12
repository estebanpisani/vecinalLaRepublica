package com.grupo9.vecinal.Repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.grupo9.vecinal.Entidades.Usuario;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {

	@Query("SELECT u FROM Usuario u WHERE u.alta = true ORDER BY u.apellido ASC, u.nombre ASC")
	public List<Usuario> usuariosAlta();
	
	@Query("SELECT u FROM Usuario u WHERE u.alta = false ORDER BY u.apellido ASC, u.nombre ASC")
	public List<Usuario> usuariosBaja();
	
	@Query("SELECT u FROM Usuario u WHERE u.apellido LIKE :apellido ORDER BY u.apellido ASC, u.nombre ASC")
	public List<Usuario> usuariosApellido(@Param("apellido") String apellido );
	
	@Query("SELECT u FROM Usuario u WHERE u.cuotaAlDia = true ORDER BY u.apellido ASC, u.nombre ASC")
	public List<Usuario> usuariosAlDia();
	
	@Query("SELECT u FROM Usuario u WHERE u.cuotaAlDia = false AND u.alta = true ORDER BY u.apellido ASC, u.nombre ASC")
	public List<Usuario> usuariosAdeudada();
	
	@Query("SELECT u FROM Usuario u WHERE u.nombreUsuario LIKE :nombreUsuario")
	public Usuario usuarioPorNombreUsuario(@Param("nombreUsuario") String nombreUsuario);


}
