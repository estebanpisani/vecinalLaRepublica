package com.grupo9.vecinal.Servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grupo9.vecinal.Entidades.Actividad;
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

	@Transactional
	public void modificarUsuario(String nombreUsuario, String contrasenia, String contrasenia2, String emailUsuario,
			String nombre, String apellido, Integer telefono, Integer id) throws Exception {
		try {
			validarDatosUsuario(nombreUsuario, contrasenia, contrasenia2, emailUsuario, nombre, apellido);
			Optional<Usuario> respuesta = usuarioRepo.findById(id);

			if (respuesta.isPresent()) {
				Usuario usuario = respuesta.get();
				usuario.setNombreUsuario(nombreUsuario);
				usuario.setContrasenia(contrasenia);
				usuario.setEmailUsuario(emailUsuario);
				usuario.setNombre(nombre);
				usuario.setApellido(apellido);
				usuario.setTelefono(telefono);

				usuarioRepo.save(usuario);

			} else {
				throw new Exception("Usuario no encontrado");
			}

		} catch (Exception e) {
			e.getMessage();
		}

	}

	@Transactional
	public void bajaUsuario(Integer id) throws Exception {
		try {
			Optional<Usuario> respuesta = usuarioRepo.findById(id);

			if (respuesta.isPresent()) {
				Usuario usuario = respuesta.get();
				usuario.setAlta(false);

				usuarioRepo.save(usuario);

			} else {
				throw new Exception("Usuario no encontrado");
			}

		} catch (Exception e) {
			e.getMessage();
		}

	}

	@Transactional(readOnly = true)
	public List<Usuario> mostrarUsuarios() throws Exception {
		try {
			return usuarioRepo.findAll();

		} catch (Exception e) {
			throw new Exception("No se encontraron afiliados");
		}

	}

	@Transactional(readOnly = true)
	public Usuario buscarUsuario(Integer id) throws Exception {
		try {
			Optional<Usuario> respuesta = usuarioRepo.findById(id);

			if (respuesta.isPresent()) {
				Usuario usuario = respuesta.get();
				return usuario;
			} else {
				throw new Exception("Usuario no encontrado");
			}

		} catch (Exception e) {
			throw new Exception("No se encontraron afiliados");
		}

	}

	@Transactional(readOnly = true)
	public List<Actividad> mostrarActividadesUsuario(Integer id) throws Exception {
		try {
			Usuario usuario = buscarUsuario(id);
			List<Actividad> actividades = usuario.getActividades();

			if (actividades.isEmpty() || actividades == null) {
				throw new Exception("No se encontraron actividades para el afiliado " + usuario.getNombre() + " "
						+ usuario.getApellido());
			} else {
				return actividades;
			}

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

	}

	@Transactional(readOnly = true)
	public List<Usuario> mostrarUsuariosAlta() throws Exception {
		try {
			return usuarioRepo.usuariosAlta();

		} catch (Exception e) {
			throw new Exception("No se encontraron afiliados");
		}

	}

	@Transactional(readOnly = true)
	public List<Usuario> mostrarUsuariosBaja() throws Exception {
		try {
			return usuarioRepo.usuariosBaja();

		} catch (Exception e) {
			throw new Exception("No se encontraron afiliados");
		}

	}

	@Transactional(readOnly = true)
	public List<Usuario> mostrarUsuariosCuotaAlDia() throws Exception {
		try {
			return usuarioRepo.usuariosAlDia();
		} catch (Exception e) {
			throw new Exception("No se encontraron afiliados");
		}

	}

	@Transactional(readOnly = true)
	public List<Usuario> mostrarUsuariosDeudores() throws Exception {
		try {
			return usuarioRepo.usuariosAdeudada();

		} catch (Exception e) {
			throw new Exception("No se encontraron afiliados");
		}

	}

	@Transactional(readOnly = true)
	public List<Usuario> mostrarUsuariosApellido(String apellido) throws Exception {
		try {
			return usuarioRepo.usuariosApellido(apellido);

		} catch (Exception e) {
			throw new Exception("No se encontraron afiliados");
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
