package com.grupo9.vecinal.Servicios;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.grupo9.vecinal.Entidades.Actividad;
import com.grupo9.vecinal.Entidades.Usuario;
import com.grupo9.vecinal.Repositorios.ActividadRepositorio;
import com.grupo9.vecinal.Repositorios.UsuarioRepositorio;

import net.bytebuddy.utility.RandomString;

@Service
public class UsuarioServicio implements UserDetailsService {
	

	

	@Autowired
	private UsuarioRepositorio usuarioRepo;

	@Autowired
	private ActividadServicio actividadServ;

	@Autowired
	private FotoServicio fotoServ;

	@Autowired
	private MailServicio enviarMails;

	@Autowired
	private ActividadRepositorio actividadRepo;
	
	public BCryptPasswordEncoder bcryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
		}

	@Transactional
	public void crearUsuario(MultipartFile foto, String nombreUsuario, String contrasenia, String contrasenia2,
			String emailUsuario, String nombre, String apellido, Long telefono) throws Exception {
		try {

			validarDatosUsuario(nombreUsuario, emailUsuario, nombre, apellido, null);
			validarContrasenia(contrasenia, contrasenia2);

			Usuario usuario = new Usuario();
			usuario.setNombreUsuario(nombreUsuario);
			String encriptada = new BCryptPasswordEncoder(4).encode(contrasenia);
			usuario.setContrasenia(encriptada);
			usuario.setEmailUsuario(emailUsuario);
			usuario.setNombre(nombre);
			usuario.setApellido(apellido);
			usuario.setTelefono(telefono);
			usuario.setCuotaAlDia(false);
			usuario.setAdmin(false);
			usuario.setAlta(false);
			usuario.setFechaDeBaja(LocalDate.now());
			usuario.setFoto(fotoServ.guardar(foto));
			String randomCode = RandomString.make(64);
			usuario.setCodValidacion(randomCode);

			usuarioRepo.save(usuario);
			enviarMails.sendVerificacionEmail(usuario);

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

	}

	@Transactional
	public void modificarUsuario(MultipartFile foto, String nombreUsuario, String emailUsuario, String nombre,
			String apellido, Long telefono, Integer id) throws Exception {
		try {

			Optional<Usuario> respuesta = usuarioRepo.findById(id);

			if (respuesta.isPresent()) {
				Usuario usuario = respuesta.get();
				validarDatosUsuario(nombreUsuario, emailUsuario, nombre, apellido, usuario);
				usuario.setNombreUsuario(nombreUsuario);
				usuario.setEmailUsuario(emailUsuario);
				usuario.setNombre(nombre);
				usuario.setApellido(apellido);
				usuario.setTelefono(telefono);
				if (!foto.isEmpty()) {
					if (usuario.getFoto() != null) {
						usuario.setFoto(fotoServ.actualizar(usuario.getFoto().getId(), foto));
					} else {
						usuario.setFoto(fotoServ.guardar(foto));
					}
				}

				usuarioRepo.save(usuario);

			} else {
				throw new Exception("Usuario no encontrado");
			}

		} catch (Exception e) {

			throw new Exception(e.getMessage());
		}

	}

	public Boolean recuperarUsuarioOContrasenia(String mail, String contrasenia, String contrasenia2) throws Exception {
		try {
			Usuario usuario = buscarUsuarioMail(mail);
			if (contrasenia == null || contrasenia.isEmpty()) {
				String randomCode = RandomString.make(64);
				usuario.setCodValidacion(randomCode);
				enviarMails.sendRecuperarDatos(usuario);
				usuarioRepo.save(usuario);
				return false;
			} else {
				validarContrasenia(contrasenia, contrasenia2);
				String encriptada = new BCryptPasswordEncoder(4).encode(contrasenia);
				usuario.setContrasenia(encriptada);
				usuario.setCodValidacion(null);
				usuarioRepo.save(usuario);
				return true;
			}

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Transactional(readOnly = true)
	public Usuario buscarUsuarioMail(String mail) throws Exception {
		Usuario usuario = usuarioRepo.usuarioPorMail(mail);
		if (usuario != null) {
			return usuario;
		} else {
			throw new Exception("El mail ingresado no corresponde a ningun usuario registrado");
		}
	}

	@Transactional
	public void modificarContrasenia(String contrasenia, String contrasenia1, String contrasenia2, Integer id)
			throws Exception {
		try {

			
			Optional<Usuario> respuesta = usuarioRepo.findById(id);

			if (respuesta.isPresent()) {
				Usuario usuario = respuesta.get();
				
				
				if (bcryptPasswordEncoder().matches(contrasenia, usuario.getContrasenia())) {
					validarContrasenia(contrasenia1, contrasenia2);
					String encriptada1 = new BCryptPasswordEncoder(4).encode(contrasenia1);
					usuario.setContrasenia(encriptada1);
					usuarioRepo.save(usuario);
				} else {
					throw new Exception("Contraseña incorrecta");
				}

			} else {
				throw new Exception("Usuario no encontrado");
			}

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

	}

	@Transactional
	public void bajaUsuario(Integer id) throws Exception {
		try {
			Optional<Usuario> respuesta = usuarioRepo.findById(id);

			if (respuesta.isPresent()) {
				Usuario usuario = respuesta.get();
				usuario.setAlta(false);
				usuario.getActividades().clear();
				usuario.setFechaDeBaja(LocalDate.now());
				String randomCode = RandomString.make(64);
				usuario.setCodValidacion(randomCode);
				usuarioRepo.save(usuario);

			} else {
				throw new Exception("Usuario no encontrado");
			}

		} catch (Exception e) {
			e.getMessage();
		}

	}

	@Transactional
	public void altaUsuario(String codigoValidacion) throws Exception {
		try {
			Usuario usuario = buscarUsuarioCodValidacion(codigoValidacion);
			if (usuario != null) {
				usuario.setAlta(true);
				usuario.setFechaDeBaja(null);
				usuario.setCodValidacion(null);
				usuarioRepo.save(usuario);
			} else {
				throw new Exception("Usuario no encontrado");
			}

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

	}

	@Transactional
	public void eliminarFotoUsuario(Integer id, Integer idFoto) {

		Usuario usuario = usuarioRepo.findById(id).get();
		usuario.setFoto(null);
		fotoServ.eliminar(idFoto);
		usuarioRepo.save(usuario);
	}

	@Transactional(readOnly = true)
	public Usuario buscarUsuarioCodValidacion(String codigo) throws Exception {
		try {
			return usuarioRepo.usuarioPorCodigoValidacion(codigo);

		} catch (Exception e) {
			throw new Exception("No se encontraron afiliados");
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
			throw new Exception("No se encontraron socios con esos datos");
		}

	}

	@Transactional(readOnly = true)
	public Usuario buscarUsuarioNobreUsuario(String nombreUsuario) throws Exception {
		try {
			Usuario usuario = usuarioRepo.usuarioPorNombreUsuario(nombreUsuario);

			if (usuario != null) {
				return usuario;
			} else {
				throw new Exception("Usuario no encontrado");
			}

		} catch (Exception e) {
			throw new Exception("No se encontraron socios con esos datos");
		}

	}

	@Transactional
	public void inscripcionActividad(Integer idUsuario, Integer idActividad) throws Exception {
		try {
			Usuario usuario = buscarUsuario(idUsuario);
			Actividad actividad = actividadServ.buscarActividad(idActividad);
			if (actividad.getCupo() <= actividad.getUsuarios().size()) {
				throw new Exception("No hay cupos disponibles para esta actividad");
			}
			for (Actividad act : usuario.getActividades()) {
				if (act.getIdActividades().equals(actividad.getIdActividades())) {
					throw new Exception("Ya estas anotado");
				}

			}
			usuario.getActividades().add(actividad);
			actividad.getUsuarios().add(usuario);
			actividad.setInscriptos();
			actividadRepo.save(actividad);
			usuarioRepo.save(usuario);

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@Transactional
	public Boolean estoyInscripto(Integer idUsuario, Integer idActividad) throws Exception {
		try {
			Usuario usuario = buscarUsuario(idUsuario);
			Actividad actividad = actividadServ.buscarActividad(idActividad);
		
			for (Actividad act : usuario.getActividades()) {
				if (act.getIdActividades().equals(actividad.getIdActividades())) {
					return true;
				}

			}
			return false;

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Transactional
	public void desinscripcionActividad(Integer idUsuario, Integer idActividad) throws Exception {
		try {
			Usuario usuario = buscarUsuario(idUsuario);
			Actividad actividad = actividadServ.buscarActividad(idActividad);
			for (Actividad act : usuario.getActividades()) {
				if (act.getIdActividades().equals(actividad.getIdActividades())) {
					usuario.getActividades().remove(actividad);
					actividad.getUsuarios().remove(usuario);
					actividad.setInscriptos();
					actividadRepo.save(actividad);
					usuarioRepo.save(usuario);					
					return;
				}

			}

			throw new Exception("No estaba anotado a este curso");

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Transactional(readOnly = true)
	public Set<Actividad> mostrarActividadesUsuario(Integer id) throws Exception {
		try {
			Usuario usuario = buscarUsuario(id);
			Set<Actividad> actividades = usuario.getActividades();

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
	public List<Usuario> mostrarUsuariosSinAdmin() throws Exception {
		try {
			return usuarioRepo.usuariosSinAdmin();

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
			return usuarioRepo.usuariosApellido("%" + apellido + "%");

		} catch (Exception e) {
			throw new Exception("No se encontraron afiliados");
		}

	}

	public void validarDatosUsuario(String nombreUsuario, String emailUsuario, String nombre, String apellido,
			Usuario usuario) throws Exception {

		if (nombreUsuario == null || nombreUsuario.isEmpty())

		{
			throw new Exception("El campo no puede estar vacio.");

		}

		List<Usuario> usuario1 = usuarioRepo.findAll();
		for (Usuario usuario2 : usuario1) {
			if (usuario2.getNombreUsuario().equalsIgnoreCase(nombreUsuario)
					&& (usuario == null || !usuario.getNombreUsuario().equalsIgnoreCase(nombreUsuario))) {
				throw new Exception(
						"El nombre de usuario: " + nombreUsuario + " ya existe, por favor ingrese uno distinto");
			}
			if (usuario2.getEmailUsuario().equals(emailUsuario)
					&& (usuario == null || !usuario.getEmailUsuario().equals(emailUsuario))) {
				throw new Exception("El mail: " + emailUsuario + " ya existe, por favor ingrese uno distinto");
			}
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

	public void validarContrasenia(String contrasenia, String contrasenia2) throws Exception {
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
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepo.usuarioPorNombreUsuario(username);
		if (usuario != null) {
			List<GrantedAuthority> permisos = new ArrayList<>();

			GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_USUARIO_REGISTRADO");
			permisos.add(p1);
			if (usuario.getAdmin()) {
				GrantedAuthority p2 = new SimpleGrantedAuthority("ROLE_USUARIO_ADMIN");
				permisos.add(p2);
			}

			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			HttpSession session = attr.getRequest().getSession(true);
			session.setAttribute("usuariologueado", usuario);

			User user = new User(usuario.getNombreUsuario(), usuario.getContrasenia(), permisos);

			return user;
		} else {
			return null;
		}

	}
}
