package com.grupo9.vecinal.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grupo9.vecinal.Entidades.Usuario;
import com.grupo9.vecinal.Repositorios.UsuarioRepositorio;

@Service
public class UsuarioServicio {

	@Autowired
	private UsuarioRepositorio usuarioRepo;

	@Transactional
	public void crearUsuario(String nombreUsuario, String contrasenia, String contrasenia2, String emailUsuario,
			String nombre, String apellido, Integer telefono) {
		try {
			validarDatosUsuario(nombreUsuario, contrasenia, contrasenia2, emailUsuario, nombre, apellido);

			Usuario usuario = new Usuario();
			usuario.setNombreUsuario(nombreUsuario);
			usuario.setContrasenia(contrasenia);
			usuario.setEmailUsuario(emailUsuario);
			usuario.setNombre(nombre);
			usuario.setApellido(apellido);
			usuario.setTelefono(telefono);
			usuario.setCuotaAlDia(false);
			usuario.setAdmin(false);
			usuario.setAlta(true);

			usuarioRepo.save(usuario);

		} catch (Exception e) {
			e.getMessage();
		}

	}

	public void validarDatosUsuario(String nombreUsuario, String contrasenia, String contrasenia2, String emailUsuario,
			String nombre, String apellido) throws Exception {

		if (nombreUsuario == null || nombreUsuario.isEmpty())

		{
			throw new Exception("El campo no puede estar vacio.");

		}

		if (contrasenia == null || contrasenia.isEmpty())

		{
			throw new Exception("El campo no puede estar vacio.");

		}

		if (contrasenia2 == null || contrasenia2.isEmpty())

		{
			throw new Exception("El campo no puede estar vacio.");

		}

		if (!contrasenia.equals(contrasenia2)) {

			throw new Exception("Las contraseñas no coiciden");

		}

		if (emailUsuario == null || emailUsuario.isEmpty())

		{
			throw new Exception("El campo no puede estar vacio.");

		}
		if (nombre == null || nombre.isEmpty())

		{
			throw new Exception("El campo no puede estar vacio.");

		}
		if (apellido == null || apellido.isEmpty())

		{
			throw new Exception("El campo no puede estar vacio.");

		}

	}

}
